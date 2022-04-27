<template>

  <!-- header -->
  <div class="nav-container page-component">
    <!--left navi #start -->
    <div class="nav left-nav">
      <div class="nav-item">
        <span class="v-link selected dark" onclick="javascript:window.location='/student'"> My Information </span>
      </div>
<!--      <div class="nav-item">-->
<!--        <span class="v-link selected dark" onclick="javascript:window.location='/order'"> My Reservations </span>-->
<!--      </div>-->
      <div class="nav-item ">
        <span class="v-link clickable" @click="logout"> Log out </span>
      </div>
    </div>
    <!-- left navi #end -->

    <!-- right navi #start -->
    <div class="page-container">
      <div class="personal-student">
        <div class="title" style="margin-top: 0px;font-size: 16px;"> Student Information </div>
        <div>
          <div class="sub-title">
            <div class="block"></div>
            Information
          </div>
          <div class="content-wrapper">
            <el-form :model="student" label-width="110px" label-position="left">
              <el-form-item label="Name：">
                <div class=""><span>{{ student.name }}</span></div>
              </el-form-item>
              <el-form-item label="Certification Type：">
                <div class=""><span>{{ student.certificatesType }}</span></div>
              </el-form-item>
              <el-form-item label="Certification Number：">
                <div class=""><span>{{ student.certificatesNo }} </span></div>
              </el-form-item>
<!--              <el-form-item label="性别：">-->
<!--                <div class=""><span>{{ student.sex == 1 ? '男' : '女' }} </span></div>-->
<!--              </el-form-item>-->
<!--              <el-form-item label="出生日期：">-->
<!--                <div class=""><span>{{ student.birthdate }} </span></div>-->
<!--              </el-form-item>-->
              <el-form-item label="Email：">
                <div class=""><span>{{ student.phone }} </span></div>
              </el-form-item>
<!--              <el-form-item label="婚姻状况：">-->
<!--                <div class=""><span>{{ student.isMarry == 1 ? '已婚' : '未婚' }} </span></div>-->
<!--              </el-form-item>-->
<!--              <el-form-item label="Current address：">-->
<!--                <div class=""><span>{{ student.provinceCode }}/{{ student.citycode }}/{{ student.districtCode }} </span></div>-->
<!--              </el-form-item>-->
              <el-form-item label="Address：">
                <div class=""><span>{{ student.address }} </span></div>
              </el-form-item>

              <br/>
<!--              <el-form-item>-->
<!--                <el-button class="v-button" type="primary" @click="remove()">删除就诊人</el-button>-->
<!--                <el-button class="v-button" type="primary white" @click="edit()">修改就诊人</el-button>-->
<!--              </el-form-item>-->

            </el-form>
          </div>
        </div>
      </div>
    </div>
    <!-- 右侧内容 #end -->
  </div>
  <!-- footer -->
</template>

<script>
import '~/assets/css/campus_personal.css'
import '~/assets/css/campus.css'
import '~/assets/css/personal.css'

import studentApi from '@/api/user/student'
import cookie from "js-cookie";

export default {

  data() {
    return {
      student: {
        param: {}
      }
    }
  },

  created() {
    this.fetchDataById();
  },

  methods: {
    fetchDataById() {
      debugger
      studentApi.getById(this.$route.query.id).then(response => {
        this.student = response.data
      })
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
.info-wrapper {
  padding-left: 0;
  padding-top: 0;
}

.content-wrapper {
  color: #333;
  font-size: 14px;
  padding-bottom: 0;
}
.el-form-item {
  margin-bottom: 5px;
}

.bottom-wrapper {
  width: 100%;
}

.button-wrapper {
  margin: 0;

}

.bottom-wrapper .button-wrapper {
  margin-top: 0;
}
</style>
