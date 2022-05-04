<template>
 <div class="app-container">
   <div id="main">
     <myheader/>
     <div class="main-container">
       <el-scrollbar class='page-component_scroll'>
         <!-- content here -->
         <nuxt/>
        </el-scrollbar>
      </div>
      <myfooter/>
    </div>
  </div>
</template>
<script>
import '~/assets/css/app.css'
import '~/assets/css/chunk.css'
import '~/assets/css/iconfont.css'
import '~/assets/css/main.css'
import myheader from './myheader'
import myfooter from './myfooter'
export default {
  components: {
    myheader,
    myfooter
  }

}
</script>
