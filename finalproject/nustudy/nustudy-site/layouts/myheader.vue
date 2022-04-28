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
          class="v-link clickable dark"
          onclick="javascript:window.location='/'"
        >
          NEU study room reservation system</span
        >
      </div>
      <div class="search-wrapper">
        <div class="campus-search animation-show">
          <!--          <el-autocomplete-->
          <!--            class="search-input-small"-->
          <!--            prefix-icon="el-icon-search"-->
          <!--            v-model="state"-->
          <!--            :fetch-suggestion="querySearchAsyc"-->
          <!--            placeholder="Campus name"-->
          <!--            @select="handleSelect">-->
          <!--            <spa slot="suffix" class="search-btn v-link highlight clickable selected">search</spa>-->
          <!--          </el-autocomplete>-->
        </div>
      </div>
      <div class="right-wrapper">
        <span class="v-link clickable">Help</span>
        <span
          v-if="name == ''"
          class="v-link clickable"
          @click="showLogin()"
          id="loginDialog"
        >
          Log in/Sign Up</span
        >
        <el-dropdown v-if="name != ''" @command="loginMenu">
          <span class="el-dropdown-link">
            {{ name }}<i class="el-icon-arrow-down el-icon--right"></i
          ></span>
          <el-dropdown-menu class="user-name-wrapper" slot="dropdown">
            <el-dropdown-item command="/student">My account</el-dropdown-item>
            <!--            <el-dropdown-item command="/order">My reservations</el-dropdown-item>-->
            <el-dropdown-item command="/logout" divided
              >Log out
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <!-- log in dialog starts here-->
    <el-dialog
      :visible.sync="dialogUserFormVisible"
      style="text-align: center"
      top="50px"
      height="600px"
      width="450px"
    >
      <div class="container">
        <div class="login">
          <el-card style="text-align: center">
            <h2>NU Study Room Reservation Login</h2>
            <el-form
              class="login-form"
              :model="model"
              :rules="rules"
              ref="form"
              @submit.native.prevent="login"
            >
              <el-form-item prop="username">
                <el-input
                  v-model="dialogAtrr.inputValue"
                  placeholder="Username"
                ></el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input
                  v-model="dialogAtrr.inputPassword"
                  placeholder="Password"
                  type="password"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <div class="send-button v-button" @click="login">
                  {{ dialogAtrr.loginBtn }}
                </div>
              </el-form-item>
              <!--              <a class="forgot-password" href="https://oxfordinformatics.com/">Forgot password ?</a>-->
            </el-form>
          </el-card>
        </div>
      </div>

      <!-- log in dialog ends here-->
    </el-dialog>
  </div>
</template>
<script>
import cookie from "js-cookie";
import Vue from "vue";
import userInfoApi from "@/api/user/userInfo";
import campusApi from "@/api/campus/campus";

const defaultDialogAtrr = {
  //showLoginType: 'email',

  labelTips: "NEU email login", // hint

  inputValue: "", // account username
  placeholder: "Please enter your email here", // input placeholder
  maxlength: 30, // input max length

  inputPassword: "", // account password
  placeholderpassword: "Please enter your password here", // placeholder
  maxlengthpassword: 30,

  loginBtn: "Log in",
};
export default {
  data() {
    return {
      userInfo: {
        email: "",
        code: "",
      },

      dialogUserFormVisible: false,
      dialogAtrr: defaultDialogAtrr,

      name: "",
    };
  },

  created() {
    this.showInfo();
  },

  mounted() {
    window.loginEvent = new Vue();
    loginEvent.$on("loginDialogEvent", function () {
      document.getElementById("loginDialog").click();
    });
  },

  methods: {
    showLogin() {
      this.dialogUserFormVisible = true;
      this.dialogAtrr = { ...defaultDialogAtrr };
    },

    login() {
      console.log("login");
      // debugger;
      (this.userInfo.email = this.dialogAtrr.inputValue),
        (this.userInfo.code = this.dialogAtrr.inputPassword);
      if (this.dialogAtrr.loginBtn == "Logging...") {
        this.$message.error("Submit multi-times");
        return;
      }

      if (this.userInfo.code == "") {
        this.$message.error("Password has to be entered");
        return;
      }

      console.log(this.userInfo.email + " " + this.userInfo.code);
      this.dialogAtrr.loginBtn = "Logging...";
      userInfoApi
        .login(this.userInfo)
        .then((response) => {
          console.log("response: " + response.data);
          this.setCookies(response.data.name, response.data.token);
        })
        .catch((e) => {
          console.log("respose: failed");
          this.dialogAtrr.loginBtn = "Log in";
        });
    },

    setCookies(name, token) {
      cookie.set("token", token, { domain: "localhost" });
      cookie.set("name", name, { domain: "localhost" });
      window.location.reload();
    },

    showInfo() {
      let token = cookie.get("token");
      if (token) {
        this.name = cookie.get("name");
        console.log(this.name);
      }
    },

    querySearchAsync(queryString, cb) {
      if (queryString == "") return;
      campusApi.getByCampusname(queryString).then((response) => {
        for (let i = 0, len = response.data.length; i < len; i++) {
          response.data[i].value = response.data[i].campusname;
        }
        cb(response.data);
      });
    },

    handleSelect(item) {
      window.location.href = "/campus/" + item.campuscode;
    },

    loginMenu(command) {
      if ("/logout" == command) {
        cookie.set("name", "", { domain: "localhost" });
        cookie.set("token", "", { domain: "localhost" });
        window.location.href = "/";
      } else {
        window.location.href = command;
      }
    },
  },
};
</script>
