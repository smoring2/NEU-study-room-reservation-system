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
              <template #default="scope">
                {{ scope.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column label="Room" width="150">
              <template #default="scope">
                {{ scope.row.title }} | {{ scope.row.docname }}
              </template>
            </el-table-column>
            <el-table-column label="Time" width="80">
              <template #default="scope">
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
<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";
import campusApi from "@/api/campus";

const route = useRoute();

const data = ref([]);
const defaultProps = {
  children: "children",
  label: "depname"
};
const campuscode = ref(null);
const activeIndex = ref(0);
const depcode = ref(null);
const depname = ref(null);
const workDate = ref(null);

const bookingScheduleList = ref([]);
const baseMap = ref({});

const page = ref(1); // current page
const limit = ref(10); // count per page
const total = ref(0); // total pages

const scheduleList = ref([]); // schedule like
const listLoading = ref(false);

// Call to query schedule details
function getDetailSchedule() {
  campusApi
    .getScheduleDetail(campuscode.value, depcode.value, workDate.value)
    .then(response => {
      scheduleList.value = response.data
    })
}

function fetchData() {
  campusApi.getDeptByCampusCode(campuscode.value).then(response => {
    data.value = response.data;
    // The first one is selected by default
    if (data.value.length > 0) {
      depcode.value = data.value[0].children[0].depcode;
      depname.value = data.value[0].children[0].depname;
      getPage();
    }
  });
}

function getPage(current = 1) {
  page.value = current;
  workDate.value = null;
  activeIndex.value = 0;
  getScheduleRule();
}

function getScheduleRule() {
  campusApi
    .getScheduleRule(page.value, limit.value, campuscode.value, depcode.value)
    .then(response => {
      bookingScheduleList.value = response.data.bookingScheduleRuleList;
      total.value = response.data.total;
      scheduleList.value = response.data.scheduleList;
      baseMap.value = response.data.baseMap;
      // pagination 后workDate=null，The first one is selected by default
      if (workDate.value == null) {
        workDate.value = bookingScheduleList.value[0].workDate;
      }
      // Call to query schedule details
      getDetailSchedule();
    });
}

function handleNodeClick(nodeData) {
  // Department categories return directly
  if (nodeData.children != null) return;
  depcode.value = nodeData.depcode;
  depname.value = nodeData.depname;
  getPage(1);
}

function selectDate(selectedWorkDate, index) {
  workDate.value = selectedWorkDate;
  activeIndex.value = index;
  // Call to query schedule details
  getDetailSchedule();
}

function getCurDate() {
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

campuscode.value = route.params.campuscode;
workDate.value = getCurDate();
fetchData();
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
