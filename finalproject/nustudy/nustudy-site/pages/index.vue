<template>
  <div class="home page-component">
    <el-carousel indicator-position="outside">
      <el-carousel-item v-for="item in 2" :key="item">
        <img src="~assets/images/banner2.jpeg" alt="" />
      </el-carousel-item>
    </el-carousel>
    <!-- search -->
    <div class="search-container">
      <div class="search-wrapper">
        <div class="hospital-search">
          <el-autocomplete
            class="search-input"
            prefix-icon="el-icon-search"
            v-model="state"
            :fetch-suggestions="querySearchAsync"
            placeholder="Enter campus here"
            @select="handleSelect"
          >
            <span
              slot="suffix"
              class="search-btn v-link highlight clickable selected"
            >
              Search
            </span>
          </el-autocomplete>
        </div>
      </div>
    </div>
    <!-- bottom -->
    <div class="bottom">
      <div class="left">
        <div class="home-filter-wrapper">
          <div class="title">Campus</div>
          <div>
            <div class="filter-wrapper">
              <span class="label">Location：</span>
              <div class="condition-wrapper">
                <span
                  v-for="(item, index) in campustypeList"
                  :key="index"
                  class="item v-link clickable"
                  :class="campustypeActiveIndex == index ? 'selected' : ''"
                  @click="campustypeSelect(item.value, index)"
                  >{{ item.name }}</span
                >
              </div>
            </div>
            <div class="filter-wrapper">
              <span class="label">District：</span>
              <div class="condition-wrapper">
                <span
                  v-for="(item, index) in districtList"
                  :key="index"
                  class="item v-link clickable"
                  :class="provinceActiveIndex == index ? 'selected' : ''"
                  @click="districtSelect(item.value, index)"
                  >{{ item.name }}</span
                >
              </div>
            </div>
          </div>
        </div>
        <div class="v-scroll-list hospital-list">
          <div
            v-for="(item, index) in list"
            :key="index"
            class="v-card clickable list-item"
          >
            <div class="">
              <div
                class="hospital-list-item hos-item"
                index="0"
                @click="show(item.campuscode)"
              >
                <div class="wrapper">
                  <div class="hospital-title">{{ item.campusname }}</div>
                  <div class="bottom-container">
                    <div class="icon-wrapper">
                      <span class="iconfont"></span>
                      {{ item.campustypeString }}
                    </div>
                    <div class="icon-wrapper">
                      <span class="iconfont"></span>
                      {{ item.bookingRule.releaseTime }} open reservations
                    </div>
                  </div>
                </div>
                <img
                  src="~assets/images/logoNeu.png"
                  alt="item.campusname"
                  class="hospital-img"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import campusApi from "@/api/campus/campus";
import dictApi from "@/api/dict";
export default {
  // asyncData：渲染组件之前异步获取数据
  asyncData({ params, error }) {
    return campusApi.getPageList(1, 10, null).then((response) => {
      console.log(response.data);
      return {
        list: response.data.content,
        pages: response.data.totalPages,
      };
    });
  },
  data() {
    return {
      searchObj: {},
      page: 1,
      limit: 10,

      campusname: "",
      campustypeList: [],
      districtList: [],

      campustypeActiveIndex: 0,
      provinceActiveIndex: 0,
      state: "",
    };
  },

  created() {
    this.init();
  },

  mounted() {
    // document.getElementById("search").style.display = 'none';
    window.addEventListener("scroll", this.load, true);
  },

  destroyed() {
    window.removeEventListener("scroll", this.load, false);
  },

  methods: {
    init() {
      dictApi.findByDictCode("Province").then((response) => {
        this.campustypeList = [];
        this.campustypeList.push({ name: "All", value: "" });
        for (let i in response.data) {
          this.campustypeList.push(response.data[i]);
        }
      });
      dictApi.findByDictCode("Beijin").then((response) => {
        this.districtList = [];
        this.districtList.push({ name: "All", value: "" });
        for (let i in response.data) {
          this.districtList.push(response.data[i]);
        }
      });
    },

    load(event) {
      // 滚动条高度为430 页面搜索消失，头部搜索显示
      // if(event.target.scrollTop > 430) {
      //   document.getElementById("search").style.display = 'block';
      // } else {
      //   document.getElementById("search").style.display = 'none';
      // }

      if (
        event.target.clientHeight + event.target.scrollTop >=
        event.target.scrollHeight
      ) {
        if (this.page < this.pages) {
          //先判断下一页是否有数据
          this.page = this.page + 1;
          this.getList(); //拉取接口数据
        }
      }
    },

    getList() {
      campusApi
        .getPageList(this.page, this.limit, this.searchObj)
        .then((response) => {
          for (let i in response.data.content) {
            this.list.push(response.data.content[i]);
          }
          this.pages = response.data.totalPages;
        });
    },

    querySearchAsync(queryString, cb) {
      this.searchObj = [];
      if (queryString == "") return;
      campusApi.getByCampusName(queryString).then((response) => {
        for (let i = 0, len = response.data.length; i < len; i++) {
          response.data[i].value = response.data[i].campusname;
        }
        cb(response.data);
      });
    },

    handleSelect(item) {
      window.location.href = "/campus/" + item.campuscode;
    },

    campustypeSelect(campustype, index) {
      this.list = [];
      this.page = 1;
      this.campustypeActiveIndex = index;
      this.searchObj.campustype = campustype;
      this.getList();
    },

    districtSelect(districtCode, index) {
      this.list = [];
      this.page = 1;
      this.provinceActiveIndex = index;
      this.searchObj.districtCode = districtCode;
      this.getList();
    },

    show(campuscode) {
      window.location.href = "/campus/" + campuscode;
    },
  },
};
</script>
