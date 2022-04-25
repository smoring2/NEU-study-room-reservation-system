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
                {{ name }}<i class="el-icon-arrow-down el-icon--right"></i></span>
          <el-dropdown-menu class="user-name-wrapper" slot="dropdown">
            <el-dropdown-item command="/student">My account</el-dropdown-item>
            <el-dropdown-item command="/order">My reservations</el-dropdown-item>
            <el-dropdown-item command="/logout" divided>Log out</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <!-- 登录弹出层 -->
    <el-dialog :visible.sync="dialogUserFormVisible" style="text-align: center;" top="50px"
               height="600px" width="450px">
      <div class="container">
        <div class="login">
          <el-card style="text-align: center">
            <h2>NU Study Room Reservation Login</h2>
            <el-form
              class="login-form"
              :model="model"
              :rules="rules"
              ref="form"
              @submit.native.prevent="login">
              <el-form-item prop="username">
                <el-input v-model="dialogAtrr.inputValue" placeholder="Username"></el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input
                  v-model="dialogAtrr.inputPassword"
                  placeholder="Password"
                  type="password"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <div class="send-button v-button" @click="login"> {{ dialogAtrr.loginBtn }}</div>
              </el-form-item>
              <!--              <a class="forgot-password" href="https://oxfordinformatics.com/">Forgot password ?</a>-->
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
  //showLoginType: 'email', // 控制手机登录与微信登录切换

  labelTips: 'NEU email login', // 输入框提示

  inputValue: '', // 输入框绑定对象
  placeholder: 'Please enter your email here', // 输入框placeholder
  maxlength: 30, // 输入框长度控制

  inputPassword: '',
  placeholderpassword: 'Please enter your password here', // 输入框placeholder
  maxlengthpassword: 30, // 输入框长度控制


  loginBtn: 'Log in', // 登录按钮或获取验证码按钮文本

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
    this.showInfo();
  },

  mounted() {
    //   // 注册全局登录事件对象
    window.loginEvent = new Vue();
    // 监听登录事件
    loginEvent.$on("loginDialogEvent", function () {
      document.getElementById("loginDialog").click();
    });
  },

  methods: {

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

      if (this.dialogAtrr.loginBtn == "Logging...") {
        this.$message.error("Submit multi-times");
        return;
      }

      if (this.userInfo.code == "") {
        this.$message.error("Password has to be entered");
        return;
      }

      console.log(this.userInfo.email + " " + this.userInfo.code)
      this.dialogAtrr.loginBtn = "Logging...";
      userInfoApi
        .login(this.userInfo)
        .then((response) => {
          console.log("response: " + response.data);
          // 登录成功 设置cookie
          this.setCookies(response.data.name, response.data.token);
        })
        .catch((e) => {
          console.log("respose: failed");
          this.dialogAtrr.loginBtn = "Log in";
        });
    },

    setCookies(name, token) {
      cookie.set("token", token, {domain: "localhost"});
      cookie.set("name", name, {domain: "localhost"});
      window.location.reload();
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
    },

    loginMenu(command) {
      if ("/logout" == command) {
        cookie.set("name", "", {domain: "localhost"});
        cookie.set("token", "", {domain: "localhost"});

        //跳转页面
        window.location.href = "/";
      } else {
        window.location.href = command;
      }
    }
  }
};
</script>
