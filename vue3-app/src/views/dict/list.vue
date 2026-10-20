<template>
  <div class="app-container">
    <!-- <div class="el-toolbar">
      <div class="el-toolbar-body" style="justify-content: flex-start;">
        <a
          href="http://localhost:8202/admin/cmn/dict/exportData"
          target="_blank"
        >
          <el-button type="text"><i class="fa fa-plus" /> 导出</el-button>
        </a>
        <el-button type="text" @click="importData"
          ><i class="fa fa-plus" /> import</el-button
        >
      </div>
    </div> -->
    <div class="el-toolbar">
      <div class="el-toolbar-body" style="justify-content: flex-start">
        <el-button type="text" @click="exportData"><i class="fa fa-plus"/> Export</el-button>
        <el-button type="text" @click="importData"><i class="fa fa-plus"/> Import</el-button>
      </div>
    </div>
    <el-table
      :data="list"
      style="width: 100%"
      row-key="id"
      border
      lazy
      :load="getChildrens"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column label="name" width="230" align="left">
        <template #default="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="code" width="220">
        <template #default="{ row }">
          {{ row.dictCode }}
        </template>
      </el-table-column>
      <el-table-column label="value" width="230" align="left">
        <template #default="scope">
          <span>{{ scope.row.value }}</span>
        </template>
      </el-table-column>

      <el-table-column label="create_time" align="center">
        <template #default="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="Import" v-model="dialogImportVisible" width="480px">
      <el-form label-position="right" label-width="170px">
        <el-form-item label="File">
          <el-upload
            :multiple="false"
            :on-success="onUploadSuccess"
            :action="'http://localhost:8202/admin/cmn/dict/importData'"
            class="upload-demo">
            <el-button size="small" type="primary">Click and upload</el-button>
            <template #tip>
              <div class="el-upload__tip">
                Excel files only and maximum size is 500kb
              </div>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogImportVisible = false">
            Cancel
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import dict from "@/api/dict"

const dialogImportVisible = ref(false) // dialog
const listLoading = ref(true)
const list = ref([]) // array of data dictionary lists

// DEBUG TRANSLATE
// import dict data
function importData() {
  // it does not work if a file is renamed to .xlsx
  // it can be tested by creating a google sheet and downloading the .xlsx version
  dialogImportVisible.value = true
}

// upload succeed
function onUploadSuccess(response, file) {
  ElMessage.info('uploded successfully!')
  // close the dialog
  dialogImportVisible.value = false
  // refresh the page
  getDictList(1)
}

// array of data dictionary lists
function getDictList(id) {
  dict.dictList(id).then(response => {
    list.value = response.data
  })
}

function getChildrens(tree, treeNode, resolve) {
  dict.dictList(tree.id).then(response => {
    resolve(response.data)
  })
}

function exportData() {
  window.location.href = 'http://localhost:8202/admin/cmn/dict/exportData'
}

getDictList(1)
</script>
