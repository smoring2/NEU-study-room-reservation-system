<template>
  <!-- header -->
  <div class="nav-container page-component">
    <!--left navi #start -->
    <!--    <div class="nav left-nav">-->
    <!--      <div class="nav-item selected">-->
    <!--        <span-->
    <!--          class="v-link selected dark"-->
    <!--          :onclick="-->
    <!--            'javascript:window.location=\'/campus/' + schedule.hoscode + '\''-->
    <!--          "-->
    <!--          >Reservation-->
    <!--        </span>-->
    <!--      </div>-->
    <!--      <div class="nav-item">-->
    <!--        <span-->
    <!--          class="v-link clickable dark"-->
    <!--          :onclick="-->
    <!--            'javascript:window.location=\'/campus/detail/' +-->
    <!--            schedule.hoscode +-->
    <!--            '\''-->
    <!--          "-->
    <!--        >-->
    <!--          Campus Intro-->
    <!--        </span>-->
    <!--      </div>-->
    <!--      <div class="nav-item">-->
    <!--        <span-->
    <!--          class="v-link clickable dark"-->
    <!--          :onclick="-->
    <!--            'javascript:window.location=\'/campus/notice/' +-->
    <!--            schedule.hoscode +-->
    <!--            '\''-->
    <!--          "-->
    <!--        >-->
    <!--          Reservation notice-->
    <!--        </span>-->
    <!--      </div>-->
    <!--      <div class="nav-item">-->
    <!--        <span class="v-link clickable dark"> Suspension information </span>-->
    <!--      </div>-->
    <!--      <div class="nav-item">-->
    <!--        <span class="v-link clickable dark"> Search/Cancel </span>-->
    <!--      </div>-->
    <!--    </div>-->

    <!-- left navi #end -->

    <!-- right navi #start -->
    <div class="page-container">
      <div class="hospital-order">
        <div class="header-wrapper">
          <div class="title mt20">Reservation Confirmation</div>
          <div>
            <div class="sub-title">
              <div class="block"></div>
              Chosen student：
            </div>
            <div class="patient-wrapper">
              <div>
                <div class="v-card clickable item">
                  <div
                    class="inline"
                    v-for="(item, index) in studentList"
                    :key="item.id"
                    @click="selectStudent(index)"
                    style="margin-right: 10px"
                  >
                    <div
                      :class="
                        activeIndex == index
                          ? 'item-wrapper selected'
                          : 'item-wrapper'
                      "
                    >
                      <div>
                        <div class="item-title">{{ item.name }}</div>
                        <div>{{ item.param.certificatesTypeString }}</div>
                        <div>{{ item.certificatesNo }}</div>
                      </div>
                      <img
                        src="//img.114yygh.com/static/web/checked.png"
                        class="checked"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="el-loading-mask" style="display: none">
                <div class="el-loading-spinner">
                  <svg viewBox="25 25 50 50" class="circular">
                    <circle
                      cx="50"
                      cy="50"
                      r="20"
                      fill="none"
                      class="path"
                    ></circle>
                  </svg>
                </div>
              </div>
            </div>
            <div class="sub-title" v-if="studentList.length > 0">
              <div class="block"></div>
              Chosen student card：
              <!--              <span class="card-tips"-->
              <!--                ><span class="iconfont"></span>-->
              <!--                如您持社保卡就诊，请务必选择医保预约挂号，以保证正常医保报销</span-->
              <!--              >-->
            </div>

            <el-card
              class="patient-card"
              shadow="always"
              v-if="studentList.length > 0"
            >
              <div slot="header" class="clearfix">
                <div>
                  <span class="name">
                    {{ student.name }}
                    {{ student.certificatesNo }} Student card </span
                  >
                </div>
              </div>
              <div class="card">
                <div class="text bind-card"></div>
              </div>
            </el-card>

            <div class="sub-title">
              <div class="block"></div>
              Information
            </div>
            <div class="content-wrapper">
              <el-form ref="form">
                <el-form-item label="Date：">
                  <div class="content">
                    <span
                      >{{ schedule.workDate }} {{ schedule.param.dayOfWeek }}
                      {{
                        schedule.workTime == 0 ? "Morning" : "Afternoon"
                      }}</span
                    >
                  </div>
                </el-form-item>
                <el-form-item label="Campus：">
                  <div class="content">
                    <span>{{ schedule.param.campusname }} </span>
                  </div>
                </el-form-item>
                <el-form-item label="Floor：">
                  <div class="content">
                    <span>{{ schedule.param.depname }} </span>
                  </div>
                </el-form-item>
                <el-form-item label="Room：">
                  <div class="content">
                    <span>{{ schedule.docname }} </span>
                  </div>
                </el-form-item>
                <el-form-item label="Title：">
                  <div class="content">
                    <span>{{ schedule.title }} </span>
                  </div>
                </el-form-item>
                <el-form-item label="Skills：">
                  <div class="content">
                    <span>{{ schedule.skill }}</span>
                  </div>
                </el-form-item>
                <!--                <el-form-item label="医事服务费：">-->
                <!--                  <div class="content">-->
                <!--                    <div class="fee">{{ schedule.amount }}</div>-->
                <!--                  </div>-->
                <!--                </el-form-item>-->
              </el-form>
            </div>

            <!-- user info #start-->
            <div>
              <div class="sub-title">
                <div class="block"></div>
                Student information
              </div>
              <div class="content-wrapper">
                <el-form ref="form" :model="form">
                  <el-form-item class="form-item" label="Student Email：">
                    {{ student.email }}
                  </el-form-item>
                </el-form>
              </div>
            </div>
            <!-- user info #end -->
            <div class="bottom-wrapper">
              <div class="button-wrapper">
                <div class="v-button" @click="submitOrder()">
                  {{ submitBnt }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- right navi #end -->
  </div>
  <!-- footer -->
</template>

<script>
import "~/assets/css/campus_personal.css";
import "~/assets/css/campus.css";

import campusApi from "@/api/campus/campus";
import studentApi from "@/api/user/student";
import orderInfoApi from "@/api/order/orderInfo";

export default {
  data() {
    return {
      scheduleId: null,
      schedule: {
        param: {},
      },
      studentList: [],
      student: {},

      activeIndex: 0,
      submitBnt: "Submit",
    };
  },

  created() {
    this.scheduleId = this.$route.query.scheduleId;
    this.init();
  },

  methods: {
    init() {
      this.getSchedule();
      this.findStudentList();
    },

    getSchedule() {
      campusApi.getSchedule(this.scheduleId).then((response) => {
        this.schedule = response.data;
      });
    },

    findStudentList() {
      studentApi.findList().then((response) => {
        this.studentList = response.data;
        if (this.studentList.length > 0) {
          this.student = this.studentList[0];
        }
      });
    },

    selectStudent(index) {
      this.activeIndex = index;
      this.student = this.studentList[index];
    },

    submitOrder() {
      if (this.student.id == null) {
        this.$message.error("Please choose the person");
        return;
      }
      if (this.submitBnt == "Submitting...") {
        this.$message.error("Can not submit at the same time");
        return;
      }

      this.submitBnt = "Submitting...";
      orderInfoApi
        .submitOrder(this.scheduleId, this.student.id)
        .then((response) => {
          let orderId = response.data;
          window.location.href = "/order/show?orderId=" + orderId;
        })
        .catch((e) => {
          this.submitBnt = "Submit";
        });
    },
  },
};
</script>
<style>
.hospital-order .header-wrapper {
  display: -webkit-box;
  display: -ms-flexbox;
  display: block !important;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
}
.hospital-order .sub-title {
  letter-spacing: 1px;
  color: #999;
  margin-top: 60px;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
}
.hospital-order .content-wrapper .content {
  color: #333;
}
.el-form-item {
  margin-bottom: 5px;
}
.hospital-order .content-wrapper {
  margin-left: 140px;
  margin-top: 20px;
}
</style>
