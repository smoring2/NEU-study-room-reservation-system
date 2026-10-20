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

<script setup>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import campusset from "@/api/campusset";

const route = useRoute();
const router = useRouter();

const campusSet = ref({});

function getHostSet(id) {
  campusset.getCampusSet(id).then(response => {
    campusSet.value = response.data;
  });
}

function save() {
  campusset.saveCampusSet(campusSet.value).then(response => {
    ElMessage({
      type: "success",
      message: "Saved!"
    });
    router.push({ path: "/campusset/list" });
  });
}

function update() {
  campusset.updateCampusSet(campusSet.value).then(response => {
    ElMessage({
      type: "success",
      message: "Updated!"
    });
    router.push({ path: "/campusset/list" });
  });
}

function saveOrUpdate() {
  if (campusSet.value.id) {
    update();
  } else {
    save();
  }
}

if (route.params && route.params.id) {
  const id = route.params.id;
  getHostSet(id);
} else {
  campusSet.value = {};
}
</script>
