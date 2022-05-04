<template>
  <!-- header -->
  <div class="nav-container page-component">
    <!-- left navi #start -->
    <div class="nav left-nav">
      <div class="nav-item selected">
        <span
          class="v-link selected dark"
          :onclick="'javascript:window.location=\'/campus/' + campuscode + '\''"
          >Start</span
        >
      </div>
      <div class="nav-item">
        <span
          class="v-link clickable dark"
          :onclick="
            'javascript:window.location=\'/campus/detail/' + campuscode + '\''
          "
        >
          Campus Intro
        </span>
      </div>
      <div class="nav-item">
        <span
          class="v-link clickable dark"
          :onclick="
            'javascript:window.location=\'/campus/notice/' + campuscode + '\''
          "
        >
          Rules
        </span>
      </div>
    </div>
    <!-- left navi #end -->

    <!-- right navi #start -->
    <div class="page-container">
      <div class="personal-order">
        <div class="title">Order</div>
        <el-form :inline="true">
          <el-form-item label="Student:">
            <el-select
              v-model="searchObj.studentId"
              placeholder="Choose the student:"
              class="v-select student-select"
            >
              <el-option
                v-for="item in studentList"
                :key="item.id"
                :label="item.name + '【' + item.certificatesNo + '】'"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="order status：" style="margin-left: 80px">
            <el-select
              v-model="searchObj.orderStatus"
              placeholder="All"
              class="v-select student-select"
              style="width: 200px"
            >
              <el-option
                v-for="item in statusList"
                :key="item.status"
                :label="item.comment"
                :value="item.status"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button
              type="text"
              class="search-button v-link highlight clickable selected"
              @click="fetchData()"
            >
              Search
            </el-button>
          </el-form-item>
        </el-form>
        <div class="table-wrapper table">
          <el-table :data="list" stripe style="width: 100%">
            <el-table-column label="Time" width="120">
              <template slot-scope="scope">
                {{ scope.row.reserveDate }}
                {{ scope.row.reserveTime === 0 ? "上午" : "下午" }}
              </template>
            </el-table-column>
            <el-table-column prop="campusname" label="Campus" width="100">
            </el-table-column>
            <el-table-column prop="depname" label="Department"> </el-table-column>
            <el-table-column prop="title" label="Room"> </el-table-column>
            <el-table-column prop="amount" label="Amount">
            </el-table-column>
            <el-table-column prop="studentName" label="Student">
            </el-table-column>
            <el-table-column prop="param.orderStatusString" label="Order status">
            </el-table-column>
            <el-table-column label="Operation">
              <template slot-scope="scope">
                <el-button
                  type="text"
                  class="v-link highlight clickable selected"
                  @click="show(scope.row.id)"
                  >Detail</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!-- page divider -->
        <el-pagination
          class="pagination"
          layout="prev, pager, next"
          :current-page="page"
          :total="total"
          :page-size="limit"
          @current-change="fetchData"
        >
        </el-pagination>
      </div>
    </div>
    <!-- right navi #end -->
  </div>
  <!-- footer -->
</template>

<script>
import "~/assets/css/campus_personal.css";
import "~/assets/css/campus.css";

import orderInfoApi from "@/api/order/orderInfo";
import studentApi from "@/api/user/student";

export default {
  data() {
    return {
      list: [],
      total: 0,
      page: 1,
      limit: 10,
      searchObj: {},

      studentList: [],
      statusList: [],
    };
  },

  created() {
    this.orderId = this.$route.query.orderId;

    this.fetchData();
    this.findStudentList();
    this.getStatusList();
  },

  methods: {
    fetchData(page = 1) {
      this.page = page;
      orderInfoApi
        .getPageList(this.page, this.limit, this.searchObj)
        .then((response) => {
          console.log(response.data);
          this.list = response.data.records;
          this.total = response.data.total;
        });
    },

    findStudentList() {
      studentApi.findList().then((response) => {
        this.studentList = response.data;
      });
    },

    getStatusList() {
      orderInfoApi.getStatusList().then((response) => {
        this.statusList = response.data;
      });
    },

    changeSize(size) {
      console.log(size);
      this.limit = size;
      this.fetchData(1);
    },

    show(id) {
      window.location.href = "/order/show?orderId=" + id;
    },
  },
};
</script>
