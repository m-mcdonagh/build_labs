import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    navclass: 'light-blue',
  },
  mutations: {
    changeNav(state, navclass) {
      state.navclass = navclass;
    },
  },
  actions: {
  },
  modules: {
  }
})
