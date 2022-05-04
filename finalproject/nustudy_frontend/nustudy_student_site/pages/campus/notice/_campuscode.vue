<template>
  <!-- header -->
  <div class="nav-container page-component">
    <!--left navi #start -->
    <div class="nav left-nav">
      <div class="nav-item">
        <span
          class="v-link clickable dark"
          :onclick="
            'javascript:window.location=\'/campus/' + campus.campuscode + '\''
          "
        >Start
        </span>
      </div>
      <div class="nav-item">
        <span
          class="v-link clickable dark"
          :onclick="
            'javascript:window.location=\'/campus/detail/' +
            campus.campuscode +
            '\''
          "
        >
          Campus Intro
        </span>
      </div>
      <div class="nav-item selected">
        <span
          class="v-link selected dark"
          :onclick="
            'javascript:window.location=\'/campus/notice/' +
            campus.campuscode +
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
      <div class="hospital-notice">
        <div class="content">
          <h2>{{ campus.campusname }}Reservation Notice</h2>
          <p>For your convenience, please read the following rules.</p>
          <h4 id="1. User Limitation：">1. User Limitation: </h4>
          <p>
            Only neu students and staff can make appointments on this website
          </p>
          <h4 id="2. Reservation rule:">2. Reservation rule:</h4>
          <ul>
            <li>
              On the same calendar day, the same campus, the same room, and the same student, you can book a maximum of
              1 number source
            </li>
          </ul>
          <h4 id="3. Cancel:">3. Cancel:</h4>
          <p>
            If you need to cancel the reserved study room, please cancel the reservation through the website at least
            before 18:00 on the previous working day. </p>
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

export default {
  data() {
    return {
      campuscode: null,
      campus: {
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
        this.campus = response.data.campus;
      });
    },
  },
};
</script>
<style>
.campus-detail .info-wrapper {
  width: 100%;
  padding-left: 0;
  padding-top: 0;
  margin-top: 0;
  flex-direction: inherit;
}

.campus-detail .info-wrapper .text {
  font-size: 14px;
}

.campus-detail .content-wrapper p {
  text-indent: 0;
}
</style>
