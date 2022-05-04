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
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column label="Campus Logo" align="center">
        <template slot-scope="scope">
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
        <template slot-scope="scope">
          {{ scope.row.status === 0 ? "Offline" : "Online" }}
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="Created Time" align="center" />

      <el-table-column label="Operation" align="center">
        <template slot-scope="scope">
          <router-link :to="'/campusset/campus/show/' + scope.row.id">
            <el-button type="primary" size="mini">Details</el-button>
          </router-link>
          <router-link
            :to="'/campusset/campus/schedule/' + scope.row.campuscode"
          >
            <el-button type="primary" size="mini">Schedule</el-button>
          </router-link>

          <el-button
            v-if="scope.row.status == 1"
            type="primary"
            size="mini"
            @click="updateStatus(scope.row.id, 0)"
            >Offline</el-button
          >
          <el-button
            v-if="scope.row.status == 0"
            type="danger"
            size="mini"
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
<script>
import campusApi from "@/api/campus";
export default {
  data() {
    return {
      listLoading: true,
      list: null,
      total: 0,
      page: 1,
      limit: 10,
      searchObj: {
        stateCode: "",
        cityCode: ""
      },
      stateList: [],
      cityList: []
    };
  },
  created() {
    this.fetchData();
    this.findAllState();
  },
  methods: {
    updateStatus(id, status) {
      campusApi.updateStatus(id, status).then(response => {
        this.fetchData(1);
      });
    },
    fetchData(page = 1) {
      this.page = page;
      campusApi
        .getCampusList(this.page, this.limit, this.searchObj)
        .then(response => {
          this.list = response.data.content;
          this.total = response.data.totalElements;
          this.listLoading = false;
        });
    },
    findAllState() {
      campusApi.findByDictCode("State").then(response => {
        this.stateList = response.data;
      });
    },
    stateChanged() {
      this.cityList = [];
      this.searchObj.cityCode = "";
      campusApi.findChildId(this.searchObj.stateCode).then(response => {
        this.cityList = response.data;
      });
    },
    changeSize() {
      this.limit = size;
      this.fetchData(1);
    }
  }
};
</script>
