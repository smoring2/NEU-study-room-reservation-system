<template>
  <!-- header -->
  <div class="nav-container page-component">
    <!-- left navi #start -->
    <div class="nav left-nav">
      <div class="nav-item">
        <span
          class="v-link clickable dark"
          :onclick="
            'javascript:window.location=\'/campus/' + hospital.campuscode + '\''
          "
          >Start
        </span>
      </div>
      <div class="nav-item selected">
        <span
          class="v-link selected dark"
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
          Reservation Rules
        </span>
      </div>
    </div>
    <!-- left navi #end -->

    <!-- right info #start -->
    <div class="page-container">
      <div class="hospital-detail">
        <div class="common-header">
          <div class="title-wrapper">
            <span class="hospital-title">{{ hospital.hosname }}</span>
            <div class="icon-wrapper">
              <span class="iconfont"></span> {{ hospital.param.hostypeString }}
            </div>
          </div>
        </div>
        <div class="info-wrapper">
          <img
            :src="'data:image/jpeg;base64,' + hospital.logoData"
            :alt="hospital.hosname"
            style="width: 80px; height: 80px"
          />
          <div class="content-wrapper">
            <div></div>
            <div></div>
            <div></div>
            <div>
              <div class="icon-text-wrapper">
                <span class="iconfont prefix-icon"></span>
                <span class="text"
                  ><p>{{ hospital.route }}</p> </span
                ><span class="iconfont right-icon"></span>
              </div>
            </div>
          </div>
        </div>
        <div class="title mt40">Cmapus Introduction</div>
        <div class="detail-content mt40">
          <p>{{ hospital.intro }}</p>
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
import cookie from "js-cookie";

import campusApi from "@/api/campus/campus";
export default {
  data() {
    return {
      campuscode: null,
      hospital: {
        param: {},
      },
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
      });
    },
  },
};
</script>
<style>
.hospital-detail .info-wrapper {
  width: 100%;
  padding-left: 0;
  padding-top: 0;
  margin-top: 0;
  flex-direction: inherit;
}

.hospital-detail .info-wrapper .text {
  font-size: 14px;
}

.hospital-detail .content-wrapper p {
  text-indent: 0;
}
</style>
