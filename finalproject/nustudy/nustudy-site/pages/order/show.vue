<template>
  <!-- header -->
  <div class="nav-container page-component">

    <!-- right info #start -->
    <div class="page-container">
      <div class="order-detail">
        <div class="title">Details</div>
        <div class="info-wrapper">
          <div class="title-wrapper">
            <div class="block"></div>
            <div>Reservation Information</div>
          </div>
          <div class="info-form">
            <el-form ref="form" :model="form">
              <el-form-item label="Name: ">
                <div class="content">
                  <span>{{ orderInfo.studentName }}</span>
                </div>
              </el-form-item>
              <el-form-item label="Date:  ">
                <div class="content">
                  <span
                    >{{ orderInfo.reserveDate }}
                    {{
                      orderInfo.reserveTime == 0
                        ? "8:00AM - 11:59 AM"
                        : "12:00 PM - 18:00 PM"
                    }}</span
                  >
                </div>
              </el-form-item>
              <el-form-item label="Campus: ">
                <div class="content">
                  <span>{{ orderInfo.campusname }} </span>
                </div>
              </el-form-item>
              <el-form-item label="Floor: ">
                <div class="content">
                  <span>{{ orderInfo.depname }} </span>
                </div>
              </el-form-item>
              <el-form-item label="Room: ">
                <div class="content">
                  <span>{{ orderInfo.title }} </span>
                </div>
              </el-form-item>
              <el-form-item label="Order No.">
                <div class="content">
                  <span>{{ orderInfo.outTradeNo }} </span>
                </div>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
    </div>
    <!-- right navi #end -->


    <el-dialog
      :visible.sync="dialogPayVisible"
      style="text-align: left"
      :append-to-body="true"
      width="500px"
      @close="closeDialog"
    >
    </el-dialog>
  </div>
  <!-- footer -->
</template>

<script>
import "~/assets/css/campus_personal.css";
import "~/assets/css/campus.css";

import orderInfoApi from "@/api/order/orderInfo";

export default {
  data() {
    return {
      orderId: null,
      orderInfo: {
        param: {},
      },

      dialogPayVisible: false,
      payObj: {},
      timer: null, // 定时器名称
    };
  },

  created() {
    this.orderId = this.$route.query.orderId;
    this.init();
  },

  methods: {
    init() {
      orderInfoApi.getOrderInfo(this.orderId).then((response) => {
        console.log(response.data);
        this.orderInfo = response.data;
        console.log("orderInfo: ", this.orderInfo);
      });
    },

    // cancelOrder() {
    //   this.$confirm("确定取消预约吗?", "提示", {
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     type: "warning",
    //   })
    //     .then(() => {
    //       // promise
    //       // 点击确定，远程调用
    //       return orderInfoApi.cancelOrder(this.orderId);
    //     })
    //     .then((response) => {
    //       this.$message.success("取消成功");
    //       this.init();
    //     })
    //     .catch(() => {
    //       this.$message.info("已取消取消预约");
    //     });
    // },

    // pay() {
    //   this.dialogPayVisible = true

    //   weixinApi.createNative(this.orderId).then(response => {
    //     this.payObj = response.data
    //     if(this.payObj.codeUrl == '') {
    //       this.dialogPayVisible = false
    //       this.$message.error("支付错误")
    //     } else {
    //       this.timer = setInterval(() => {
    //         this.queryPayStatus(this.orderId)
    //       }, 3000);
    //     }
    //   })
    // },

    // queryPayStatus(orderId) {
    //   weixinApi.queryPayStatus(orderId).then(response => {
    //     debugger
    //     if (response.message == '支付中') {
    //       return
    //     }
    //     clearInterval(this.timer);
    //     window.location.reload()
    //   })
    // },

    closeDialog() {
      debugger;
      if (this.timer) {
        clearInterval(this.timer);
      }
    },
  },
};
</script>
<style>
.info-wrapper {
  padding-left: 0;
  padding-top: 0;
}

.content-wrapper {
  color: #333;
  font-size: 14px;
  padding-bottom: 0;
}

.bottom-wrapper {
  width: 100%;
}

.button-wrapper {
  margin: 0;
}

.el-form-item {
  margin-bottom: 5px;
}

.bottom-wrapper .button-wrapper {
  margin-top: 0;
}
</style>
