import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/login',
    name: 'login',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import('../views/Login.vue') /* webpackChunkName: "login" */ 
  },
  {
    path: '/lab',
    name: 'lab',
    component: () => import('../views/Lab.vue')
  },
  {
    path: '/create',
    name: 'create',
    component: () => import('../views/Create.vue')
  },
  {
    path: '/partbuilder',
    name: 'partbuilder',
    component: () => import('../views/PartBuilder.vue')
  },
  {
    path: '/labbuilder',
    name: 'labbuilder',
    component: () => import('../views/LabBuilder.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
