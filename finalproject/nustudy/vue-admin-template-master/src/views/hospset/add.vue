<template>
  <div class="app-container">
    Campus Set Add
    <el-form label-width="120px">
      <el-form-item label="Campus Name">
        <el-input v-model="hospitalSet.hosname" />
      </el-form-item>
      <el-form-item label="Campus Code">
        <el-input v-model="hospitalSet.hoscode" />
      </el-form-item>
      <el-form-item label="API Url">
        <el-input v-model="hospitalSet.apiUrl" />
      </el-form-item>
      <el-form-item label="Contact (Name)">
        <el-input v-model="hospitalSet.contactsName" />
      </el-form-item>
      <el-form-item label="Contact (Phone)">
        <el-input v-model="hospitalSet.contactsPhone" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveOrUpdate">Save</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import hospset from "@/api/hospset";

export default {
  data() {
    return {
      hospitalSet: {}
    };
  },
  created() {
    if (this.$route.params && this.$route.params.id) {
      const id = this.$route.params.id;
      this.getHostSet(id);
    } else {
      this.hospitalSet = {};
    }
  },
  methods: {
    getHostSet(id) {
      hospset.getHospSet(id).then(response => {
        this.hospitalSet = response.data;
      });
    },
    save() {
      hospset.saveHospSet(this.hospitalSet).then(response => {
        this.$message({
          type: "success",
          message: "Saved!"
        });
        this.$router.push({ path: "/hospSet/list" });
      });
    },
    update() {
      hospset.updateHospSet(this.hospitalSet).then(response => {
        this.$message({
          type: "success",
          message: "Updated!"
        });
        this.$router.push({ path: "/hospSet/list" });
      });
    },
    saveOrUpdate() {
      if (this.hospitalSet.id) {
        this.update();
      } else {
        this.save();
      }
    }
  }
};
</script>
