<template>
  <div class="app-container">
    Campus Set List
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="searchObj.campusname" placeholder="Campus Name" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchObj.campuscode" placeholder="Campus Code" />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()"
        >Search</el-button
      >
    </el-form>

    <div>
      <el-button type="danger" size="mini" @click="removeRows()"
        >Delete All Chosen Records</el-button
      >
    </div>
    <el-table
      :data="list"
      stripe
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <!-- <el-table-column prop="index" label="No." width="50" /> -->
      <el-table-column prop="campusname" label="Campus Name" />
      <el-table-column prop="campuscode" label="Campus Code" />
      <el-table-column prop="apiUrl" label="API Url" width="180" />
      <el-table-column prop="contactsName" label="Contact (Name)" />
      <el-table-column prop="contactsPhone" label="Contact (Phone)" />
      <el-table-column label="Status" width="60">
        <template slot-scope="scope">
          {{ scope.row.status === 1 ? "online" : "offline" }}
        </template>
      </el-table-column>
      <el-table-column label="operations" width="280" align="center">
        <template slot-scope="scope">
          <!-- Delete -->
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeDataById(scope.row.id)"
            >Delete</el-button
          >
          <!-- Lcok -->
          <el-button
            v-if="scope.row.status == 1"
            type="primary"
            size="mini"
            icon="el-icon-delete"
            @click="lockHospSet(scope.row.id, 0)"
            >Lock</el-button
          >
          <!-- Unlock -->
          <el-button
            v-if="scope.row.status == 0"
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="lockHospSet(scope.row.id, 1)"
            >Unlock</el-button
          >
          <router-link :to="'/campusset/edit/' + scope.row.id">
            <el-button
              type="primary"
              size="mini"
              icon="el-icon-edit"
            ></el-button>
          </router-link>
        </template>
      </el-table-column>
    </el-table>
    <!-- Pagination -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding:30px 0;text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>
</template>
<script>
import campusset from "@/api/campusset";

export default {
  data() {
    return {
      current: 1,
      limit: 5,
      searchObj: {},
      list: [],
      total: 0,
      multipleSelection: 0
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList(page = 1) {
      this.current = page;
      campusset
        .getCampusSetList(this.current, this.limit, this.searchObj)
        .then(response => {
          this.list = response.data.records;
          this.total = response.data.total;
          console.log(response);
        })
        .error(error => {
          console.log(error);
        });
    },

    removeDataById(id) {
      this.$confirm("It will delete this record.", "Notice", {
        confirmButtonText: "Yes",
        cancelButtonText: "Cancel",
        type: "warning"
      }).then(() => {
        campusset.deleteCampusSet(id).then(response => {
          this.$message({
            type: "success",
            message: "Deleted!"
          });
          this.getList(1);
        });
      });
    },

    removeRows() {
      this.$confirm("It will delete all these chosen record.", "Notice", {
        confirmButtonText: "Yes",
        cancelButtonText: "Cancel",
        type: "warning"
      }).then(() => {
        var idList = [];
        for (var i = 0; i < this.multipleSelection.length; i++) {
          var obj = this.multipleSelection[i];
          var id = obj.id;
          idList.push(id);
        }
        campusset.batchRemoveCampusSet(idList).then(result => {
          this.$message({
            type: "success",
            message: "Deleted!"
          });
          this.getList(1);
        });
      });
    },
    handleSelectionChange(selection) {
      console.log(selection);
      this.multipleSelection = selection;
    },
    lockHospSet(id, status) {
      campusset.lockCampusSet(id, status).then(response => {
        this.getList();
      });
    }
  }
};
</script>
