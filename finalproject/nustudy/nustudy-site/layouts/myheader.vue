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
        <span class="text"> NEU study room reservation system</span>
      </div>
      <div class="search-wrapper">
        <div class="campus-search animation-show">
          <el-autocomplete
            class="search-input-small"
            prefix-icon="el-icon-search"
            v-model="state"
            :fetch-suggestion="querySearchAsyc"
            placeholder="Campus name"
            @select="handleSelect">
            <span
              slot="suffix"
              class="search-btn v-link highlight clickable selected">search</span>
          </el-autocomplete>
        </div>
      </div>
      <div class="right-wrapper">
        <span class="v-link clickable">Help</span>
        <span v-if="name == ''" class="v-link clickable" @click="showLogin()" id="loginDialog">
          Log in/Sign Up</span>
        <el-dropdown v-if="name != ''" @command="loginMenu">
              <span class="el-dropdown-link">
                {{ name }}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
          <el-dropdown-menu class="user-name-wrapper" slot="dropdown">
            <!--            <el-dropdown-item command="/user">实名认证</el-dropdown-item>-->
            <el-dropdown-item command="/order">Your reservations</el-dropdown-item>
            <!--            <el-dropdown-item command="/patient">就诊人管理</el-dropdown-item>-->
            <el-dropdown-item command="/logout" divided>Log out</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <!-- 登录弹出层 -->
    <el-dialog :visible.sync="dialogUserFormVisible" style="text-align: center;" top="50px"
               height="600px" width="600px" @close="closeDialog()">
      <div class="container">
        <!-- 手机登录 #start -->
<!--        <div class="operate-view" v-if="dialogAtrr.showLoginType === 'email'">-->
<!--          <div class="wrapper" style="width: 90%">-->
<!--            <div class="mobile-wrapper" style="position: static;width: 80%">-->
<!--              <span class="title">{{ dialogAtrr.labelTips }}</span>-->
<!--              <el-form>-->
<!--                <el-form-item>-->
<!--                  <el-input v-model="dialogAtrr.inputValue" :placeholder="dialogAtrr.placeholder" :maxlength="dialogAtrr.maxlength" class="input v-input">-->
<!--                  </el-input>-->
<!--                </el-form-item>-->
<!--                <el-form-item>-->
<!--                  <el-input v-model="dialogAtrr.inputPassword" :placeholder="dialogAtrr.placeholderpassword"-->
<!--                            :maxlength="dialogAtrr.maxlengthpassword" class="input v-input">-->
<!--                  </el-input>-->
<!--                </el-form-item>-->
<!--              </el-form>-->
<!--              <div class="send-button v-button" @click="btnClick()"> {{ dialogAtrr.loginBtn }}</div>-->
<!--            </div>-->
<!--          </div>-->
<!--        </div>-->
        <div class="login">
          <el-card>
            <h2>Login</h2>
            <el-form
              class="login-form"
              :model="model"
              :rules="rules"
              ref="form"
              @submit.native.prevent="login"
            >
              <el-form-item prop="username">
                <el-input v-model="dialogAtrr.inputValue" placeholder="Username" prefix-icon="fas fa-user"></el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input
                  v-model="dialogAtrr.inputPassword"
                  placeholder="Password"
                  type="password"
                  prefix-icon="fas fa-lock"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <div class="send-button v-button" @click="btnClick()"> {{ dialogAtrr.loginBtn }}</div>
              </el-form-item>
              <a class="forgot-password" href="https://oxfordinformatics.com/">Forgot password ?</a>
            </el-form>
          </el-card>
        </div>
      </div>

      <!-- 手机登录 #end -->

    </el-dialog>
  </div>

</template>
<script>
import cookie from 'js-cookie'
import Vue from 'vue'
import userInfoApi from '@/api/user/userInfo'
import campusApi from "@/api/campus/campus"

const defaultDialogAtrr = {
  showLoginType: 'email', // 控制手机登录与微信登录切换

  labelTips: 'NEU email login', // 输入框提示

  inputValue: '', // 输入框绑定对象
  placeholder: 'Please enter your email here', // 输入框placeholder
  maxlength: 30, // 输入框长度控制

  inputPassword: '',
  placeholderpassword: 'Please enter your password here', // 输入框placeholder
  maxlengthpassword: 30, // 输入框长度控制


  loginBtn: 'Log in', // 登录按钮或获取验证码按钮文本

  // sending: true,      // 是否可以发送验证码
  // second: -1,        // 倒计时间  second>0 : 显示倒计时 second=0 ：重新发送 second=-1 ：什么都不显示
  // clearSmsTime: null  // 倒计时定时任务引用 关闭登录层清除定时任务
}
export default {
  data() {
    return {
      userInfo: {
        email: "",
        code: "",
        openid: "",
      },

      dialogUserFormVisible: false,
      // 弹出层相关属性
      dialogAtrr: defaultDialogAtrr,

      name: "", // 用户登录显示的名称
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
    btnClick() {
      this.login();
    },

    // // 绑定登录，点击显示登录层
    showLogin() {
      this.dialogUserFormVisible = true;
      // 初始化登录层相关参数
      this.dialogAtrr = {...defaultDialogAtrr};
    },

    // // 登录
    login() {
      console.log("login")
      // debugger;
      this.userInfo.email = this.dialogAtrr.inputValue,
        this.userInfo.code = this.dialogAtrr.inputPassword;

      if (this.dialogAtrr.loginBtn == "正在提交...") {
        this.$message.error("重复提交");
        return;
      }

      if (this.userInfo.code == "") {
        this.$message.error("验证码必须输入");
        return;
      }

      console.log(this.userInfo.email + " " + this.userInfo.code)
      this.dialogAtrr.loginBtn = "正在提交...";
      userInfoApi
        .login(this.userInfo)
        .then((response) => {
          console.log("respose: " + response.data);
          // 登录成功 设置cookie
          this.setCookies(response.data.name, response.data.token);
        })
        .catch((e) => {
          console.log("respose: failed");
          this.dialogAtrr.loginBtn = "马上登录";
        });
    },

    setCookies(name, token) {
      cookie.set("token", token, {domain: "localhost"});
      cookie.set("name", name, {domain: "localhost"});
      window.location.reload();
    },

    // // 获取验证码
    // getCodeFun() {
    //   if (!/^1[34578]\d{9}$/.test(this.userInfo.email)) {
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
    //     .sendCode(this.userInfo.email)
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

    //   this.dialogAtrr.labelTips = "验证码已发送至" + this.userInfo.email;
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
    closeDialog() {
    },

    showInfo() {
      let token = cookie.get("token");
      if (token) {
        this.name = cookie.get("name");
        console.log(this.name);
      }
    },
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
    }

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

    //   emailLogin() {
    //     this.dialogAtrr.showLoginType = "email";
    //     this.showLogin();
    //   },
  }
};
</script>
