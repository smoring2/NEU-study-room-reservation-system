<template>
  <div class="header-container">
    <div class="wrapper">
      <!-- logo -->
      <div class="left-wrapper v-link selected">
        <img
          style="width: 50px"
          width="50"
          height="50"
          src="~assets/images/logoNeu.png"
        />
        <span
          class="v-link selected dark"
          :onclick="'javascript:window.location='"
        >
          NEU Study Room Reservation System</span
        >
      </div>
      <div class="search-wrapper">
        <div class="campus-search animation-show">
          <el-autocomplete
            class="search-input-small"
            prefix-icon="el-icon-search"
            v-model="state"
            :fetch-suggestion="querySearchAsyc"
            placeholder="Campus name"
            @select="handleSelect"
          >
            <span
              slot="suffix"
              class="search-btn v-link highlight clickable selected"
              >search</span
            >
          </el-autocomplete>
        </div>
      </div>
      <div class="right-wrapper">
        <span class="v-link clickable">Help</span>
        <!--        <el-dropdown >-->
        <!--              <span class="el-dropdown-link">-->
        <!--                晴天<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
        <!--              </span>-->
        <!--            <el-dropdown-menu class="user-name-wrapper" slot="dropdown">-->
        <!--                <el-dropdown-item>挂号订单</el-dropdown-item>-->
        <!--                <el-dropdown-item>就诊人管理</el-dropdown-item>-->
        <!--                <el-dropdown-item divided>退出登录</el-dropdown-item>-->
        <!--            </el-dropdown-menu>-->
        <!--        </el-dropdown>-->
        <span class="v-link clickable" @click="dialogUserFormVisible = true">
          Log in/Sign Up
        </span>
      </div>
    </div>
  </div>
</template>
<script>
import campusApi from "@/api/campus/campus";

export default {
  data() {
    return {
      userInfo: {
        phone: "347-651-8167",
        code: "",
        openid: "",
      },

      dialogUserFormVisible: false,
      // 弹出层相关属性
      // dialogAtrr: defaultDialogAtrr,

      name: "CS6650", // 用户登录显示的名称
    };
  },

  created() {
    // this.showInfo();
  },

  mounted() {
    //   // 注册全局登录事件对象
    //   window.loginEvent = new Vue();
    //   // 监听登录事件
    //   loginEvent.$on("loginDialogEvent", function () {
    //     document.getElementById("loginDialog").click();
    //   });
    //   // 触发事件，显示登录层：loginEvent.$emit('loginDialogEvent')
    //   //初始化微信js
    //   const script = document.createElement("script");
    //   script.type = "text/javascript";
    //   script.src =
    //     "https://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js";
    //   document.body.appendChild(script);
    //   // 微信登录回调处理
    //   let self = this;
    //   window["loginCallback"] = (name, token, openid) => {
    //     debugger;
    //     self.loginCallback(name, token, openid);
    //   };
  },

  methods: {
    // loginCallback(name, token, openid) {
    //   // 打开手机登录层，绑定手机号，改逻辑与手机登录一致
    //   if (openid != "") {
    //     this.userInfo.openid = openid;

    //     this.showLogin();
    //   } else {
    //     this.setCookies(name, token);
    //   }
    // },

    // // 绑定登录或获取验证码按钮
    // btnClick() {
    //   // 判断是获取验证码还是登录
    //   if (this.dialogAtrr.loginBtn == "获取验证码") {
    //     this.userInfo.phone = this.dialogAtrr.inputValue;

    //     // 获取验证码
    //     this.getCodeFun();
    //   } else {
    //     // 登录
    //     this.login();
    //   }
    // },

    // // 绑定登录，点击显示登录层
    // showLogin() {
    //   this.dialogUserFormVisible = true;

    //   // 初始化登录层相关参数
    //   this.dialogAtrr = { ...defaultDialogAtrr };
    // },

    // // 登录
    // login() {
    //   debugger;
    //   this.userInfo.code = this.dialogAtrr.inputValue;

    //   if (this.dialogAtrr.loginBtn == "正在提交...") {
    //     this.$message.error("重复提交");
    //     return;
    //   }

    //   if (this.userInfo.code == "") {
    //     this.$message.error("验证码必须输入");
    //     return;
    //   }
    //   if (this.userInfo.code.length != 6) {
    //     this.$message.error("验证码格式不正确");
    //     return;
    //   }

    //   this.dialogAtrr.loginBtn = "正在提交...";
    //   userInfoApi
    //     .login(this.userInfo)
    //     .then((response) => {
    //       console.log(response.data);
    //       // 登录成功 设置cookie
    //       this.setCookies(response.data.name, response.data.token);
    //     })
    //     .catch((e) => {
    //       this.dialogAtrr.loginBtn = "马上登录";
    //     });
    // },

    // setCookies(name, token) {
    //   cookie.set("token", token, { domain: "localhost" });
    //   cookie.set("name", name, { domain: "localhost" });
    //   window.location.reload();
    // },

    // // 获取验证码
    // getCodeFun() {
    //   if (!/^1[34578]\d{9}$/.test(this.userInfo.phone)) {
    //     this.$message.error("手机号码不正确");
    //     return;
    //   }

    //   // 初始化验证码相关属性
    //   this.dialogAtrr.inputValue = "";
    //   this.dialogAtrr.placeholder = "请输入验证码";
    //   this.dialogAtrr.maxlength = 6;
    //   this.dialogAtrr.loginBtn = "马上登录";

    //   // 控制重复发送
    //   if (!this.dialogAtrr.sending) return;

    //   // 发送短信验证码
    //   this.timeDown();
    //   this.dialogAtrr.sending = false;
    //   smsApi
    //     .sendCode(this.userInfo.phone)
    //     .then((response) => {
    //       this.timeDown();
    //     })
    //     .catch((e) => {
    //       this.$message.error("发送失败，重新发送");
    //       // 发送失败，回到重新获取验证码界面
    //       this.showLogin();
    //     });
    // },

    // // 倒计时
    // timeDown() {
    //   if (this.clearSmsTime) {
    //     clearInterval(this.clearSmsTime);
    //   }
    //   this.dialogAtrr.second = 60;

    //   this.dialogAtrr.labelTips = "验证码已发送至" + this.userInfo.phone;
    //   this.clearSmsTime = setInterval(() => {
    //     --this.dialogAtrr.second;
    //     if (this.dialogAtrr.second < 1) {
    //       clearInterval(this.clearSmsTime);
    //       this.dialogAtrr.sending = true;
    //       this.dialogAtrr.second = 0;
    //     }
    //   }, 1000);
    // },

    // // 关闭登录层
    // closeDialog() {
    //   if (this.clearSmsTime) {
    //     clearInterval(this.clearSmsTime);
    //   }
    // },

    // showInfo() {
    //   let token = cookie.get("token");
    //   if (token) {
    //     this.name = cookie.get("name");
    //     console.log(this.name);
    //   }
    // },

    // loginMenu(command) {
    //   if ("/logout" == command) {
    //     cookie.set("name", "", { domain: "localhost" });
    //     cookie.set("token", "", { domain: "localhost" });

    //     //跳转页面
    //     window.location.href = "/";
    //   } else {
    //     window.location.href = command;
    //   }
    // },

    // 搜索
    querySearchAsync(queryString, cb) {
      if (queryString == "") return;
      campusApi.getByHosname(queryString).then((response) => {
        for (let i = 0, len = response.data.length; i < len; i++) {
          response.data[i].value = response.data[i].hosname;
        }
        cb(response.data);
      });
    },

    handleSelect(item) {
      window.location.href = "/campus/" + item.hoscode;
    },

    //   weixinLogin() {
    //     this.dialogAtrr.showLoginType = "weixin";

    //     weixinApi.getLoginParam().then((response) => {
    //       var obj = new WxLogin({
    //         self_redirect: true,
    //         id: "weixinLogin", // 需要显示的容器id
    //         appid: response.data.appid, // 公众号appid wx*******
    //         scope: response.data.scope, // 网页默认即可
    //         redirect_uri: response.data.redirectUri, // 授权成功后回调的url
    //         state: response.data.state, // 可设置为简单的随机数加session用来校验
    //         style: "black", // 提供"black"、"white"可选。二维码的样式
    //         href: "", // 外部css文件url，需要https
    //       });
    //     });
    //   },

    //   phoneLogin() {
    //     this.dialogAtrr.showLoginType = "phone";
    //     this.showLogin();
    //   },
  },
};
</script>
