<template>
  <div class="app-container">
    <div style="margin-bottom: 10px;font-size: 10px;">
      Choose:{{ baseMap.campusname }} / {{ depname }} / {{ workDate }}
    </div>
    <el-container style="height: 100%">
      <el-aside width="200px" style="border: 1px silver solid">
        <!-- department -->
        <el-tree
          :data="data"
          :props="defaultProps"
          :default-expand-all="true"
          @node-click="handleNodeClick"
        />
      </el-aside>
      <el-main style="padding: 0 0 0 20px;">
        <el-row style="width: 100%">
          <!-- Schedule date pagination  -->
          <el-tag
            v-for="(item, index) in bookingScheduleList"
            :key="item.id"
            @click="selectDate(item.workDate, index)"
            :type="index == activeIndex ? '' : 'info'"
            style="height: 60px;margin-right: 5px;margin-right:15px;cursor:pointer;"
          >
            {{ item.workDate }} {{ item.dayOfWeek }}<br />
            {{ item.availableNumber }} / {{ item.reservedNumber }}
          </el-tag>

          <!-- pagination  -->
          <el-pagination
            :current-page="page"
            :total="total"
            :page-size="limit"
            class="pagination"
            layout="prev, pager, next"
            @current-change="getPage"
          >
          </el-pagination>
        </el-row>

        <el-row style="margin-top: 20px;">
          <!-- reservation room corresponding to the date -->
          <el-table
            v-loading="listLoading"
            :data="scheduleList"
            border
            fit
            highlight-current-row
          >
            <el-table-column label="No" width="60" align="center">
              <template slot-scope="scope">
                {{ scope.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column label="Room" width="150">
              <template slot-scope="scope">
                {{ scope.row.title }} | {{ scope.row.docname }}
              </template>
            </el-table-column>
            <el-table-column label="Time" width="80">
              <template slot-scope="scope">
                {{ scope.row.workTime == 0 ? "Morning" : "Afternoon" }}
              </template>
            </el-table-column>
            <el-table-column
              prop="reservedNumber"
              label="Reserve Number"
              width="80"
            />
            <el-table-column
              prop="availableNumber"
              label="Available Number"
              width="100"
            />
            <el-table-column prop="amount" label="Amount" width="90" />
            <el-table-column prop="skill" label="Skill" />
          </el-table>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>
<script>
import campusApi from "@/api/campus";
export default {
  data() {
    return {
      data: [],
      defaultProps: {
        children: "children",
        label: "depname"
      },
      campuscode: null,
      activeIndex: 0,
      depcode: null,
      depname: null,
      workDate: null,

      bookingScheduleList: [],
      baseMap: {},

      page: 1, // current page
      limit: 10, // count per page
      total: 0, // total pages

      scheduleList: [] // schedule like
    };
  },
  created() {
    this.campuscode = this.$route.params.campuscode;
    this.workDate = this.getCurDate();
    this.fetchData();
  },
  methods: {
    // Call to query schedule details
    getDetailSchedule() {
      campusApi
        .getScheduleDetail(this.campuscode, this.depcode, this.workDate)
        .then(response => {
          this.scheduleList = response.data
        })
    },

    fetchData() {
      campusApi.getDeptByCampusCode(this.campuscode).then(response => {
        this.data = response.data;
        // The first one is selected by default
        if (this.data.length > 0) {
          this.depcode = this.data[0].children[0].depcode;
          this.depname = this.data[0].children[0].depname;
          this.getPage();
        }
      });
    },
    getPage(page = 1) {
      this.page = page;
      this.workDate = null;
      this.activeIndex = 0;
      this.getScheduleRule();
    },

    getScheduleRule() {
      campusApi
        .getScheduleRule(this.page, this.limit, this.campuscode, this.depcode)
        .then(response => {
          this.bookingScheduleList = response.data.bookingScheduleRuleList;
          this.total = response.data.total;
          this.scheduleList = response.data.scheduleList;
          this.baseMap = response.data.baseMap;
          // pagination 后workDate=null，The first one is selected by default
          if (this.workDate == null) {
            this.workDate = this.bookingScheduleList[0].workDate;
          }
          // Call to query schedule details
          this.getDetailSchedule();
        });
    },

    handleNodeClick(data) {
      // Department categories return directly
      if (data.children != null) return;
      this.depcode = data.depcode;
      this.depname = data.depname;
      this.getPage(1);
    },

    selectDate(workDate, index) {
      this.workDate = workDate;
      this.activeIndex = index;
      // Call to query schedule details
      this.getDetailSchedule();
    },

    getCurDate() {
      var datetime = new Date();
      var year = datetime.getFullYear();
      var month =
        datetime.getMonth() + 1 < 10
          ? "0" + (datetime.getMonth() + 1)
          : datetime.getMonth() + 1;
      var date =
        datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
      return year + "-" + month + "-" + date;
    }
  }
};
</script>
<style>
.el-tree-node.is-current > .el-tree-node__content {
  background-color: #409eff !important;
  color: white;
}

.el-checkbox__input.is-checked + .el-checkbox__label {
  color: black;
}
</style>
