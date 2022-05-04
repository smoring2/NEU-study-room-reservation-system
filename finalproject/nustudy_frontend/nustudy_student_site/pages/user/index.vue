<template>

  <!-- header -->
  <div class="nav-container page-component">
    <!--left navi #start -->
    <div class="nav left-nav">
      <div class="nav-item">
        <span class="v-link selected dark" onclick="javascript:window.location='/student'"> My Information </span>
      </div>
      <div class="nav-item">
        <span class="v-link selected dark" onclick="javascript:window.location='/order'"> My Reservations </span>
      </div>
      <div class="nav-item ">
        <span class="v-link clickable dark" @click="logout">Log out </span>
      </div>

    </div>
    <!-- left navi #end -->

    <!-- right navi #start -->
    <div class="page-container">
      <div>
        <div class="title"> My Information </div>
        <div class="form-wrapper" v-if="userInfo.authStatus == 0">
          <div>
            <el-form :model="userAuah" label-width="110px" label-position="left">
              <el-form-item prop="name" label="Name：" class="form-normal">
                <div class="name-input">
                  <el-input v-model="userAuah.name" placeholder="Name" class="input v-input"/>
                </div>
              </el-form-item>
              <el-form-item prop="certificatesType" label="Certification Type：">
                <el-select v-model="userAuah.certificatesType" placeholder="Certification Type" class="v-select patient-select">
                  <el-option
                    v-for="item in certificatesTypeList"
                    :key="item.value"
                    :label="item.name"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item prop="certificatesNo" label="Certification Number：">
                <el-input v-model="userAuah.certificatesNo" placeholder="Certification Number" class="input v-input"/>
              </el-form-item>
              <el-form-item prop="name" label="Upload Certification：">
                <div class="upload-wrapper">
                  <div class="avatar-uploader">
                    <el-upload
                      class="avatar-uploader"
                      :action="fileUrl"
                      :show-file-list="false"
                      :on-success="onUploadSuccess">
                      <div class="upload-inner-wrapper">
                        <img v-if="userAuah.certificatesUrl" :src="userAuah.certificatesUrl" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        <div v-if="!userAuah.certificatesUrl" class="text"> Upload Certification</div>
                      </div>
                    </el-upload>
                  </div>
                  <img src="//img.114yygh.com/static/web/auth_example.png" class="example">
                </div>
              </el-form-item>
            </el-form>

            <div class="bottom-wrapper">
              <div class="button-wrapper">
                <div class="v-button" @click="saveUserAuah()">{{ submitBnt }}</div>
              </div>
            </div>
          </div>
        </div>

        <div class="context-container" v-if="userInfo.authStatus != 0">
          <div>
            <el-form :model="formData" label-width="110px" label-position="right">
              <el-form-item prop="name" label="Name：" class="form-normal">
                <div class="name-input">
                  {{ userInfo.name }}
                </div>
              </el-form-item>
              <el-form-item prop="name" label="Certification Type：">
                {{ userInfo.param.certificatesTypeString }}
              </el-form-item>
              <el-form-item prop="name" label="Certification Number：">
                {{ userInfo.certificatesNo }}
              </el-form-item>
            </el-form>
          </div>
        </div>

      </div>
    </div>
    <!-- right navi #end -->

  </div>
  <!-- footer -->
</template>

<script>
import '~/assets/css/campus_personal.css'
import '~/assets/css/campus.css'
import '~/assets/css/personal.css'

import dictApi from '@/api/dict'
import userInfoApi from '@/api/user/userInfo'
import cookie from "js-cookie";

const defaultForm = {
  name: '',
  certificatesType: '',
  certificatesNo: '',
  certificatesUrl: ''
}
export default {

  data() {
    return {
      userAuah: defaultForm,
      certificatesTypeList: [],
      fileUrl:'http://localhost/api/oss/file/fileUpload?fileHost=userAuah',

      userInfo: {
        param: {}
      },

      submitBnt: 'Submit'
    }
  },

  created() {
    this.init()
  },

  methods: {
    init() {
      this.getUserInfo()

      this.getDict()
    },

    getUserInfo() {
      userInfoApi.getUserInfo().then(response => {
        this.userInfo = response.data
      })
    },

    saveUserAuah() {
      if(this.submitBnt == 'Submitting') {
        this.$message.info('Submit multi-times')
        return
      }

      this.submitBnt = 'Submitting...'
      userInfoApi.saveUserAuah(this.userAuah).then(response => {
        this.$message.success("Succeed")
        window.location.reload()
      }).catch(e => {
        this.submitBnt = 'Submit'
      })
    },

    getDict() {
      dictApi.findByDictCode("CertificatesType").then(response => {
        this.certificatesTypeList = response.data
      })
    },

    onUploadSuccess(response, file) {
      debugger
      if(response.code !== 200) {
        this.$message.error("Upload failed")
        return
      }
      this.userAuah.certificatesUrl = file.response.data
    },
    logout() {
      console.log("logout")
      cookie.set("name", "", {domain: "localhost"});
      cookie.set("token", "", {domain: "localhost"});
      window.location.href = "/";
    }
  }
}
</script>
<style>
  .header-wrapper .title {
    font-size: 16px;
    margin-top: 0;
  }

  .content-wrapper {
    margin-left: 0;
  }

  .patient-card .el-card__header .detail {
    font-size: 14px;
  }

  .page-container .title {
    letter-spacing: 1px;
    font-weight: 700;
    color: #333;
    font-size: 16px;
    margin-top: 0;
    margin-bottom: 20px;
  }

  .page-container .tips {
    width: 100%;
    padding-left: 0;
  }

  .page-container .form-wrapper {
    padding-left: 92px;
    width: 580px;
  }

  .form-normal {
    height: 40px;
  }
  .bottom-wrapper{
    width: 100%;
    padding: 0;
    margin-top: 0;
  }
</style>
