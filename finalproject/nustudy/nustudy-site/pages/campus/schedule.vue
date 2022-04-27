<template>
  <!-- header -->
  <div class="nav-container page-component">
    <!--left navi #start -->
<!--    <div class="nav left-nav">-->
<!--      <div class="nav-item selected">-->
<!--        <span-->
<!--          class="v-link selected dark"-->
<!--          :onclick="'javascript:window.location=\'/campus/' + hoscode + '\''"-->
<!--          >Reservation-->
<!--        </span>-->
<!--      </div>-->
<!--      <div class="nav-item">-->
<!--        <span-->
<!--          class="v-link clickable dark"-->
<!--          :onclick="-->
<!--            'javascript:window.location=\'/campus/detail/' + hoscode + '\''-->
<!--          "-->
<!--        >-->
<!--          Campus Detail-->
<!--        </span>-->
<!--      </div>-->
<!--      <div class="nav-item">-->
<!--        <span-->
<!--          class="v-link clickable dark"-->
<!--          :onclick="-->
<!--            'javascript:window.location=\'/campus/notice/' + hoscode + '\''-->
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
    <!-- Left navi #end -->
    <!--left navi #start -->
    <div class="nav left-nav">
      <div class="nav-item selected">
        <span
          class="v-link selected dark"
          :onclick="
            'javascript:window.location=\'/campus/' + campuscode + '\''
          ">Start</span>
      </div>
      <div class="nav-item">
        <span
          class="v-link clickable dark"
          :onclick="
            'javascript:window.location=\'/campus/detail/' +
            campuscode +
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
            campuscode +
            '\''
         "
        >
          Rules
        </span>
      </div>
    </div>
    <!-- left navi #end -->

    <!-- Right navi #start -->
    <div class="page-container">
      <div class="hospital-source-list">
        <div class="header-wrapper" style="justify-content: normal">
          <span class="v-link clickable" @click="show()">{{
            baseMap.hosname
          }}</span>
          <div class="split"></div>
          <div>{{ baseMap.bigname }}</div>
        </div>
        <div class="title mt20">{{ baseMap.depname }}</div>
        <!-- time zone list #start -->
        <div class="mt60">
          <div class="title-wrapper">{{ baseMap.workDateString }}</div>
          <div class="calendar-list-wrapper">
            <!-- item.depNumber == -1 ? 'gray space' : item.depNumber == 0 ? 'gray' : 'small small-space'-->
            <!-- selected , index == activeIndex ? 'selected' : ''-->
            <div
              :class="'calendar-item ' + item.curClass"
              style="width: 124px"
              v-for="(item, index) in bookingScheduleList"
              :key="item.id"
              @click="selectDate(item, index)"
            >
              <div class="date-wrapper">
                <span>{{ item.workDate }}</span
                ><span class="week">{{ item.dayOfWeek }}</span>
              </div>
              <div class="status-wrapper" v-if="item.status == 0">
                {{
                  item.availableNumber == -1
                    ? "Not open"
                    : item.availableNumber == 0
                    ? "Full already"
                    : "Available"
                }}
              </div>
              <div class="status-wrapper" v-if="item.status == 1">Coming soon</div>
              <div class="status-wrapper" v-if="item.status == -1">
                Stopped
              </div>
            </div>
          </div>
          <!-- Page Divider -->
          <el-pagination
            class="pagination"
            layout="prev, pager, next"
            :current-page="page"
            :total="total"
            :page-size="limit"
            @current-change="getPage"
          >
          </el-pagination>
        </div>

        <!-- Start booking  #start-->
        <div class="countdown-wrapper mt60" v-if="!tabShow">
          <div class="countdonw-title">
            {{ time
            }}<span class="v-link selected">{{ baseMap.releaseTime }} </span
            >Releasing
          </div>
          <div class="countdown-text">
            Countdown
            <div>
              <span class="number">{{ timeString }}</span>
            </div>
          </div>
        </div>
        <!-- Start booking #end-->

        <!-- morning #start -->
        <div class="mt60" v-if="tabShow">
          <div class="">
            <div class="list-title">
              <div class="block"></div>
              8:00 AM - 11:50 AM
            </div>
            <div
              v-for="item in scheduleList"
              :key="item.id"
              v-if="item.workTime == 0"
            >
              <div class="list-item">
                <div class="item-wrapper">
                  <div class="title-wrapper">
                    <div class="title">{{ item.title }}</div>
                    <div class="split"></div>
                    <div class="name">{{ item.docname }}</div>
                  </div>
                  <div class="special-wrapper">{{ item.skill }}</div>
                </div>
                <div class="right-wrapper">
<!--                  <div class="fee">{{ item.amount }}</div>-->
                  <div class="button-wrapper">
                    <div
                      class="v-button"
                      @click="booking(item.hosScheduleId, item.availableNumber)"
                      :style="
                        item.availableNumber == 0 || pageFirstStatus == -1
                          ? 'background-color: #7f828b;'
                          : ''
                      "
                    >
                      <span v-if="item.availableNumber !== 0"
                        >Book
                        <!-- <span class="number">{{ item.availableNumber }}</span> -->
                      </span>
                      <span v-if="item.availableNumber === 0"
                        >Reserved
                        <!-- <span class="number">{{ item.availableNumber }}</span> -->
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- morning #end -->
        <!-- afternoon #start -->
        <div class="mt60" v-if="tabShow">
          <div class="">
            <div class="list-title">
              <div class="block"></div>
              12:00 PM - 17:59 PM
            </div>
            <div
              v-for="item in scheduleList"
              :key="item.id"
              v-if="item.workTime == 1"
            >
              <div class="list-item">
                <div class="item-wrapper">
                  <div class="title-wrapper">
                    <div class="title">{{ item.title }}</div>
                    <div class="split"></div>
                    <div class="name">{{ item.docname }}</div>
                  </div>
                  <div class="special-wrapper">{{ item.skill }}</div>
                </div>
                <div class="right-wrapper">
<!--                  <div class="fee">￥{{ item.amount }}</div>-->
                  <div class="button-wrapper">
                    <div
                      class="v-button"
                      @click="booking(item.hosScheduleId, item.availableNumber)"
                      :style="
                        item.availableNumber == 0 || pageFirstStatus == -1
                          ? 'background-color: #7f828b;'
                          : ''
                      "
                    >
                      <span v-if="item.availableNumber !== 0"
                        >Book
                        <!-- <span class="number">{{ item.availableNumber }}</span> -->
                      </span>
                      <span v-if="item.availableNumber === 0"
                        >Reserved
                        <!-- <span class="number">{{ item.availableNumber }}</span> -->
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- afternoon #end -->
      </div>
    </div>
    <!-- Right navi #end -->
  </div>

  <!-- footer -->
</template>

<script>
import "~/assets/css/campus_personal.css";
import "~/assets/css/campus.css";

import campusApi from "@/api/campus/campus";
export default {
  data() {
    return {
      campuscode: null,
      depcode: null,
      workDate: null,

      bookingScheduleList: [],
      scheduleList: [],
      baseMap: {},
      nextWorkDate: null,
      preWorkDate: null,

      tabShow: true,
      activeIndex: 0,

      page: 1,
      limit: 7,
      total: 1,

      timeString: null,
      time: "今天",
      timer: null,

      pageFirstStatus: 0, // status of first item in the first page
    };
  },

  created() {
    this.campuscode = this.$route.query.hoscode;
    this.depcode = this.$route.query.depcode;
    this.workDate = this.getCurDate();

    this.getBookingScheduleRule();
  },

  methods: {
    getPage(page = 1) {
      this.page = page;
      this.workDate = null;
      this.activeIndex = 0;

      this.getBookingScheduleRule();
    },

    getBookingScheduleRule() {
      campusApi
        .getBookingScheduleRule(
          this.page,
          this.limit,
          this.campuscode,
          this.depcode
        )
        .then((response) => {
          this.bookingScheduleList = response.data.bookingScheduleList;
          console.log("bookingScheduleList: ", this.bookingScheduleList);
          this.total = response.data.total;
          this.baseMap = response.data.baseMap;

          this.dealClass();

          if (this.workDate == null) {
            this.workDate = this.bookingScheduleList[0].workDate;
          }
          if (this.workDate == this.getCurDate()) {
            this.pageFirstStatus = this.bookingScheduleList[0].status;
          } else {
            this.pageFirstStatus = 0;
          }
          this.findScheduleList();
        });
    },

    findScheduleList() {
      campusApi
        .findScheduleList(this.campuscode, this.depcode, this.workDate)
        .then((response) => {
          this.scheduleList = response.data;
          console.log("scheduleList: ", this.scheduleList);
        });
    },

    selectDate(item, index) {
      this.workDate = item.workDate;
      this.activeIndex = index;

      //清理定时
      if (this.timer != null) clearInterval(this.timer);

      if (item.status == 1) {
        this.tabShow = false;
        let releaseTime = new Date(
          this.getCurDate() + " " + this.baseMap.releaseTime
        ).getTime();
        let nowTime = new Date().getTime();
        this.countDown(releaseTime, nowTime);

        this.dealClass();
      } else {
        this.tabShow = true;

        this.getBookingScheduleRule();
      }
    },

    dealClass() {
      for (let i = 0; i < this.bookingScheduleList.length; i++) {
        // depNumber -1:not open 0：full >0：available
        let curClass =
          this.bookingScheduleList[i].availableNumber == -1
            ? "gray space"
            : this.bookingScheduleList[i].availableNumber == 0
            ? "gray"
            : "small small-space";
        curClass += i == this.activeIndex ? " selected" : "";
        this.bookingScheduleList[i].curClass = curClass;
      }
    },

    getCurDate() {
      let datetime = new Date();
      let year = datetime.getFullYear();
      let month =
        datetime.getMonth() + 1 < 10
          ? "0" + (datetime.getMonth() + 1)
          : datetime.getMonth() + 1;
      let date =
        datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
      return year + "-" + month + "-" + date;
    },

    countDown(releaseTime, nowTime) {
      let secondes = 0;
      if (releaseTime > nowTime) {
        this.time = "Today";
        secondes = Math.floor((releaseTime - nowTime) / 1000);
      } else {
        this.time = "Tomorrow";
        let releaseDate = new Date(releaseTime);
        releaseTime = new Date(
          releaseDate.setDate(releaseDate.getDate() + 1)
        ).getTime();
        secondes = Math.floor((releaseTime - nowTime) / 1000);
      }

      this.timer = setInterval(() => {
        secondes = secondes - 1;
        if (secondes <= 0) {
          clearInterval(timer);
          this.init();
        }
        this.timeString = this.convertTimeString(secondes);
      }, 1000);
      this.$once("hook:beforeDestroy", () => {
        clearInterval(timer);
      });
    },

    convertTimeString(allseconds) {
      if (allseconds <= 0) return "00:00:00";
      let days = Math.floor(allseconds / (60 * 60 * 24));
      let hours = Math.floor((allseconds - days * 60 * 60 * 24) / (60 * 60));
      let minutes = Math.floor(
        (allseconds - days * 60 * 60 * 24 - hours * 60 * 60) / 60
      );
      let seconds =
        allseconds - days * 60 * 60 * 24 - hours * 60 * 60 - minutes * 60;

      let timString = "";
      if (days > 0) {
        timString = days + "Days:";
      }
      return (timString += hours + " : " + minutes + " : " + seconds + "  ");
    },

    show() {
      window.location.href = "/campus/" + this.hoscode;
    },

    booking(scheduleId, availableNumber) {
      console.log("scheduleId: ", scheduleId);
      if (availableNumber == 0 || this.pageFirstStatus == -1) {
        this.$message.error("Can not book!");
      } else {
        window.location.href = "/campus/booking?scheduleId=" + scheduleId;
      }
    },
  },
};
</script>
