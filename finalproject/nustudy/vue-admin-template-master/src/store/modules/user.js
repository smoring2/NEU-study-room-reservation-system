import { login, logout, getInfo } from "@/api/login";
import { getToken, setToken, removeToken } from "@/utils/auth";

const user = {
  state: {
    token: getToken(),
    name: "",
    avatar: "",
    roles: []
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token;
    },
    SET_NAME: (state, name) => {
      state.name = name;
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar;
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles;
    }
  },

  actions: {
    // login
    Login({ commit }, userInfo) {
      const data = { token: "admin" };
      setToken(data.token);
      commit("SET_TOKEN", data.token);


    },

    // Get user information
    GetInfo({ commit, state }) {
      const data = {
        roles: "admin",
        name: "admin",
        avatar:
          "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
      };
      if (data.roles && data.roles.length > 0) {
        // Verify that the returned roles is a non-empty array
        commit("SET_ROLES", data.roles);
      } else {
        reject("getInfo: roles must be a non-null array !");
      }
      commit("SET_NAME", data.name);
      commit("SET_AVATAR", data.avatar);


    },

    // logout
    LogOut({ commit, state }) {
      commit("SET_TOKEN", "");
      commit("SET_ROLES", []);
      removeToken();

    },

    // frontend logout
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit("SET_TOKEN", "");
        removeToken();
        resolve();
      });
    }
  }
};

export default user;
