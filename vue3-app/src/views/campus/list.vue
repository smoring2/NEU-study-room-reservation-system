<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-select
          v-model="searchObj.stateCode"
          placeholder="State"
          @change="stateChanged"
        >
          <el-option
            v-for="item in stateList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-select v-model="searchObj.cityCode" placeholder="City">
          <el-option
            v-for="item in cityList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-input v-model="searchObj.campusname" placeholder="Campus" />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="fetchData()"
        >Search</el-button
      >
      <el-button type="default" @click="resetData()">Clear</el-button>
    </el-form>

    <!-- banner -->
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="No." width="60" align="center">
        <template #default="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column label="Campus Logo" align="center">
        <template #default="scope">
          <img
            :src="'data:image/jpeg;base64,' + scope.row.logoData"
            width="80"
          />
        </template>
      </el-table-column>

      <el-table-column prop="campusname" label="Campus Name" align="center" />
      <el-table-column
        prop="param.campustypeString"
        label="Area"
        align="center"
      />
      <el-table-column
        prop="param.fullAddress"
        label="Location"
        align="center"
      />
      <el-table-column label="Status" align="center">
        <template #default="scope">
          {{ scope.row.status === 0 ? "Offline" : "Online" }}
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="Created Time" align="center" />

      <el-table-column label="Operation" align="center">
        <template #default="scope">
          <router-link :to="'/campusset/campus/show/' + scope.row.id">
            <el-button type="primary" size="small">Details</el-button>
          </router-link>
          <router-link
            :to="'/campusset/campus/schedule/' + scope.row.campuscode"
          >
            <el-button type="primary" size="small">Schedule</el-button>
          </router-link>

          <el-button
            v-if="scope.row.status == 1"
            type="primary"
            size="small"
            @click="updateStatus(scope.row.id, 0)"
            >Offline</el-button
          >
          <el-button
            v-if="scope.row.status == 0"
            type="danger"
            size="small"
            @click="updateStatus(scope.row.id, 1)"
            >Online</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- pagination -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      style="padding: 30px 0; text-align: center;"
      layout="sizes, prev, pager, next, jumper, ->, total, slot"
      @current-change="fetchData"
      @size-change="changeSize"
    />
  </div>
</template>
<script setup>
import { ref, reactive } from "vue";
import campusApi from "@/api/campus";

const listLoading = ref(true);
const list = ref(null);
const total = ref(0);
const page = ref(1);
const limit = ref(10);
const searchObj = reactive({
  stateCode: "",
  cityCode: ""
});
const stateList = ref([]);
const cityList = ref([]);

function updateStatus(id, status) {
  campusApi.updateStatus(id, status).then(response => {
    fetchData(1);
  });
}

function fetchData(current = 1) {
  page.value = current;
  campusApi
    .getCampusList(page.value, limit.value, searchObj)
    .then(response => {
      list.value = response.data.content;
      total.value = response.data.totalElements;
      listLoading.value = false;
    });
}

function findAllState() {
  campusApi.findByDictCode("State").then(response => {
    stateList.value = response.data;
  });
}

function stateChanged() {
  cityList.value = [];
  searchObj.cityCode = "";
  campusApi.findChildId(searchObj.stateCode).then(response => {
    cityList.value = response.data;
  });
}

// the page-size emitted by el-pagination is now received as a parameter
// (the Vue 2 version read an undeclared `size` identifier)
function changeSize(size) {
  limit.value = size;
  fetchData(1);
}

fetchData();
findAllState();
</script>
