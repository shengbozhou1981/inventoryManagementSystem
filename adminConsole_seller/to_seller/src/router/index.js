import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

import ProductManage from "../views/ProductManage"

import OrderManage from "../views/OrderManage"

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'ProductModule',
    component: Home,
    redirect: '/productManage',
    show:true,
    children: [

      {
        path:'/productManage',
        name:'ProductManagement',
        show:true,
        component: ProductManage
      }

    ]
  },
  {
    path: '/',
    name: 'OrderModule',
    component: Home,
    show:true,
    children:[
      {
        path:'/orderManage',
        name:'OrderManagement',
        show:true,
        component: OrderManage
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
