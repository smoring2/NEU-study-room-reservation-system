<template>
  <!-- header -->
  <div class="nav-container page-component">
    <!--left navi #start -->
    <div class="nav left-nav">
      <div class="nav-item selected">
        <span
          class="v-link selected dark"
          :onclick="
            'javascript:window.location=\'/campus/' + hospital.campuscode + '\''
          "
          >Start</span
        >
      </div>
      <div class="nav-item">
        <span
          class="v-link clickable dark"
          :onclick="
            'javascript:window.location=\'/campus/detail/' +
            hospital.campuscode +
            '\''
          "
        >
          Campus Intro
        </span>
      </div>
      <div class="nav-item">
        <span
          class="v-link clickable dark"
          :onclick="
            'javascript:window.location=\'/campus/notice/' +
            hospital.campuscode +
            '\''
          "
        >
          Rules
        </span>
      </div>
    </div>
    <!-- left navi #end -->

    <!-- right info #start -->
    <div class="page-container">
      <div class="hospital-home">
        <div class="common-header">
          <div class="title-wrapper">
            <span class="hospital-title">{{ hospital.campusname }}</span>
            <div class="icon-wrapper">
              {{ hospital.param.campustypeString }}
            </div>
          </div>
        </div>
        <div class="info-wrapper">
          <img
            class="hospital-img"
            :src="'data:image/jpeg;base64,' + hospital.logoData"
            :alt="hospital.campusname"
          />
          <div class="content-wrapper">
            <div>Reservation Rules</div>
            <div class="line">
              <div>
                <span class="label"> Booking cycle: </span
                ><span>{{ bookingRule.cycle }}Days</span>
              </div>
              <div class="space">
                <span class="label">Begin：</span
                ><span>{{ bookingRule.releaseTime }}</span>
              </div>
              <div class="space">
                <span class="label">End：</span
                ><span>{{ bookingRule.stopTime }}</span>
              </div>
            </div>
            <div class="line">
              <!--              <span class="label">退号时间：</span>-->
              <!--              <span v-if="bookingRule.quitDay == -1"-->
              <!--                >就诊前一工作日{{ bookingRule.quitTime }}前取消</span>-->
              <span v-if="bookingRule.quitDay == 0"
                >就诊前当天{{ bookingRule.quitTime }}前取消</span
              >
            </div>
            <div style="margin-top: 20px">Booking rules:</div>
            <div class="rule-wrapper">
              <ol>
                <li v-for="item in bookingRule.rule" :key="item">{{ item }}</li>
              </ol>
            </div>
          </div>
        </div>
        <div class="title select-title">Building And Floor</div>
        <div class="select-dept-wrapper">
          <div class="department-wrapper">
            <div class="hospital-department">
              <div class="dept-list-wrapper el-scrollbar" style="height: 100%">
                <div
                  class="dept-list el-scrollbar__wrap"
                  style="margin-bottom: -17px; margin-right: -17px"
                >
                  <div class="el-scrollbar__view">
                    <div
                      class="sub-item"
                      v-for="(item, index) in departmentVoList"
                      :key="item.id"
                      :class="index == activeIndex ? 'selected' : ''"
                      @click="move(index, item.depcode)"
                    >
                      {{ item.depname }}
                    </div>
                  </div>
                </div>
                <div class="el-scrollbar__bar is-horizontal">
                  <div
                    class="el-scrollbar__thumb"
                    style="transform: translateX(0%)"
                  ></div>
                </div>
                <div class="el-scrollbar__bar is-vertical">
                  <div
                    class="el-scrollbar__thumb"
                    style="transform: translateY(0%); height: 91.4761%"
                  ></div>
                </div>
              </div>
            </div>
          </div>
          <div class="sub-dept-container">
            <div
              v-for="(item, index) in departmentVoList"
              :key="item.id"
              :class="index == 0 ? 'selected' : ''"
              class="sub-dept-wrapper"
              :id="item.depcode"
            >
              <div class="sub-title">
                <div class="block selected"></div>
                {{ item.depname }}
              </div>
              <div class="sub-item-wrapper">
                <div
                  v-for="it in item.children"
                  :key="it.id"
                  class="sub-item"
                  @click="schedule(it.depcode)"
                >
                  <span class="v-link clickable">{{ it.depname }} </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 右侧内容 #end -->
  </div>
  <!-- footer -->
</template>

<script>
import "~/assets/css/campus_personal.css";
import "~/assets/css/campus.css";

import cookie from "js-cookie";
import campusApi from "@/api/campus/campus";
// import userInfoApi from "@/api/user/userInfo";

export default {
  data() {
    return {
      campuscode: null,
      activeIndex: 0,

      hospital: {
        param: {},
      },
      bookingRule: {},
      departmentVoList: [],
    };
  },

  created() {
    this.campuscode = this.$route.params.campuscode;

    this.init();
  },

  methods: {
    init() {
      campusApi.show(this.campuscode).then((response) => {
        this.hospital = response.data.hospital;
        this.bookingRule = response.data.bookingRule;
      });

      campusApi.findDepartment(this.campuscode).then((response) => {
        this.departmentVoList = response.data;
      });
    },

    move(index, depcode) {
      this.activeIndex = index;
      document.getElementById(depcode).scrollIntoView();
    },

    schedule(depcode) {
      // debugger;
      let token = cookie.get("token");
      if (!token) {
        loginEvent.$emit("loginDialogEvent");
        return;
      }

      // //判断认证
      // userInfoApi.getUserInfo().then((response) => {
      //   let authStatus = response.data.authStatus;
      //   // 状态为2认证通过
      //   if (!authStatus || authStatus != 2) {
      //     window.location.href = "/user";
      //     return;
      //   }
      // });

      window.location.href =
        "/campus/schedule?campuscode=" +
        this.hospital.campuscode +
        "&depcode=" +
        depcode;
    },
  },
};
</script>
