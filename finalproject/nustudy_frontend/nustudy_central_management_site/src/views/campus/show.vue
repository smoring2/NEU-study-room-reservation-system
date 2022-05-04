<template>
  <div class="app-container">
    <h4>Basics</h4>
    <table
      class="table table-striped table-condenseda table-bordered"
      width="100%"
    >
      <tbody>
        <tr>
          <th width="15%">Campus Name</th>
          <td width="35%">
            <b style="font-size: 14px">{{ campus.campusname }}</b> |
            {{ campus.param.campustypeString }}
          </td>
          <th width="15%">Logo</th>
          <td width="35%">
            <img
              :src="'data:image/jpeg;base64,' + campus.logoData"
              width="80"
            />
          </td>
        </tr>
        <tr>
          <th>Campus Code</th>
          <td>{{ campus.campuscode }}</td>
          <th>Address</th>
          <td>{{ campus.param.fullAddress }}</td>
        </tr>
        <tr>
          <th>Route</th>
          <td colspan="3">{{ campus.route }}</td>
        </tr>
        <tr>
          <th>Introduction</th>
          <td colspan="3">{{ campus.intro }}</td>
        </tr>
      </tbody>
    </table>

    <h4>Reservation Rules</h4>
    <table
      class="table table-striped table-condenseda table-bordered"
      width="100%"
    >
      <tbody>
        <tr>
          <th width="15%">Cycle</th>
          <td width="35%">{{ bookingRule.cycle }}day</td>
          <th width="15%">Start Time</th>
          <td width="35%">{{ bookingRule.releaseTime }}</td>
        </tr>
        <tr>
          <th>End Time</th>
          <td>{{ bookingRule.stopTime }}</td>
          <th>Cancel Time</th>
          <td>
            {{ bookingRule.quitTime }}{{ bookingRule.quitDay == -1 ? " the day before visitor" : "visitor day" }}
          </td>
        </tr>
        <tr>
          <th>Rules</th>
          <td colspan="3">
            <ol>
              <li v-for="item in bookingRule.rule" :key="item">{{ item }}</li>
            </ol>
          </td>
        </tr>
        <el-row>
          <el-button @click="back">Back</el-button>
        </el-row>
      </tbody>
    </table>
  </div>
</template>
<script>
import campusApi from "@/api/campus";
export default {
  data() {
    return {
      campus: null, //hospital information
      bookingRule: null //Reservation information
    };
  },
  created() {
    const id = this.$route.params.id;
    this.fetachCampusDetail(id);
  },
  methods: {
    fetachCampusDetail(id) {
      campusApi.getCampusById(id).then(response => {
        this.campus = response.data.campus //DEBUG change response of controller, CampusServiceImpl/getCampusById()
        this.bookingRule = response.data.bookingRule
      })
    },
    back() {
      this.$router.push({ path: "/campusset/campus/list" });
    }
  }
}
</script>
