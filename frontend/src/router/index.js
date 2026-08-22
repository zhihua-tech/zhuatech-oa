/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import MainLayout from '../components/MainLayout.vue'
const routes=[
 {path:'/login',component:LoginView,meta:{public:true,title:'登录'}},
 {path:'/',component:MainLayout,children:[
  {path:'',component:()=>import('../views/HomeView.vue'),meta:{title:'首页'}}, {path:'workbench',component:()=>import('../views/WorkbenchView.vue'),meta:{title:'工作台'}}, {path:'contacts',component:()=>import('../views/ContactsView.vue'),meta:{title:'通讯录'}}, {path:'profile',component:()=>import('../views/ProfileView.vue'),meta:{title:'我的'}},
  {path:'notices',component:()=>import('../views/NoticesView.vue'),meta:{title:'企业公告'}}, {path:'attendance',component:()=>import('../views/AttendanceView.vue'),meta:{title:'考勤打卡'}}, {path:'leave',component:()=>import('../views/LeaveView.vue'),meta:{title:'请假审批'}}, {path:'tasks',component:()=>import('../views/TasksView.vue'),meta:{title:'我的待办'}}
 ]}
]
const router=createRouter({history:createWebHistory(),routes,scrollBehavior:()=>({top:0})})
router.beforeEach(to=>{ document.title=`${to.meta.title || '移动办公'}｜知华科技 OA`; if(!to.meta.public&&!localStorage.getItem('zhuatech_token')) return '/login'; if(to.path==='/login'&&localStorage.getItem('zhuatech_token')) return '/' })
export default router
