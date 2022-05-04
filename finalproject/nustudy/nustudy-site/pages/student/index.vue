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
        <span class ="v-link clickable" @click="logout"> Log out </span>
      </div>
    </div>
    <!-- left navi #end -->

    <!-- right #start -->
    <div class="page-container">
      <div class="personal-student">
        <div class="header-wrapper">
          <div class="title">My Information</div>
        </div>
        <div class="content-wrapper">
          <el-card class="student-card" shadow="always" v-for="item in studentList" :key="item.id">
            <div slot="header" class="clearfix">
              <div>
                <span class="name">{{ item.name }}</span>
                <span>{{ item.certificatesNo }} {{ item.certificatesType }}</span>
                <div class="detail" @click="show(item.id)"> See more <span class="iconfont"></span></div>
              </div>
            </div>
          </el-card>
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

import studentApi from '@/api/user/student'
import cookie from "js-cookie";

export default {

  data() {
    return {
      studentList: []
    }
  },

  created() {
    this.findStudentList()
  },

  methods: {
    findStudentList() {
      studentApi.findList().then(response => {
        this.studentList = response.data
      })
    },


    show(id) {
      window.location.href = '/student/show?id=' + id
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

.student-card .el-card__header .detail {
  font-size: 14px;
}

</style>
