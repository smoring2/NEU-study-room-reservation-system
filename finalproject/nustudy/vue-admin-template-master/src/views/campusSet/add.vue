<template>
  <div class="app-container">
    Campus Set Add
    <el-form label-width="120px">
      <el-form-item label="Campus Name">
        <el-input v-model="campusSet.campusname" />
      </el-form-item>
      <el-form-item label="Campus Code">
        <el-input v-model="campusSet.campuscode" />
      </el-form-item>
      <el-form-item label="API Url">
        <el-input v-model="campusSet.apiUrl" />
      </el-form-item>
      <el-form-item label="Contact (Name)">
        <el-input v-model="campusSet.contactsName" />
      </el-form-item>
      <el-form-item label="Contact (Phone)">
        <el-input v-model="campusSet.contactsPhone" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveOrUpdate">Save</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import campusset from "@/api/campusset";

export default {
  data() {
    return {
      campusSet: {}
    };
  },
  created() {
    if (this.$route.params && this.$route.params.id) {
      const id = this.$route.params.id;
      this.getHostSet(id);
    } else {
      this.campusSet = {};
    }
  },
  methods: {
    getHostSet(id) {
      campusset.getCampusSet(id).then(response => {
        this.campusSet = response.data;
      });
    },
    save() {
      campusset.saveCampusSet(this.campusSet).then(response => {
        this.$message({
          type: "success",
          message: "Saved!"
        });
        this.$router.push({ path: "/campusset/list" });
      });
    },
    update() {
      campusset.updateCampusSet(this.campusSet).then(response => {
        this.$message({
          type: "success",
          message: "Updated!"
        });
        this.$router.push({ path: "/campusset/list" });
      });
    },
    saveOrUpdate() {
      if (this.campusSet.id) {
        this.update();
      } else {
        this.save();
      }
    }
  }
};
</script>
