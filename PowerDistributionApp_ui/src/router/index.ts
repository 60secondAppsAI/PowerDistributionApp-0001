import { createRouter, createWebHashHistory } from 'vue-router';
import { useAuth } from '../stores/auth';
import SignIn from '../components/SignIn.vue';
import Settings from '../components/Settings.vue';
import MyDashboard from '../components/MyDashboard.vue';

// Dynamic imports for module components
import PowerDistributionUnits from '../components/PowerDistributionUnits.vue';
import PowerDistributionUnitDetail from '../components/PowerDistributionUnitDetail.vue';
import PowerPanels from '../components/PowerPanels.vue';
import PowerPanelDetail from '../components/PowerPanelDetail.vue';
import PowerOutlets from '../components/PowerOutlets.vue';
import PowerOutletDetail from '../components/PowerOutletDetail.vue';
import PowerFeeds from '../components/PowerFeeds.vue';
import PowerFeedDetail from '../components/PowerFeedDetail.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: MyDashboard
  },
  
  // Dynamic module routes
  {
    path: '/powerDistributionUnits',
    name: 'PowerDistributionUnits',
    component: PowerDistributionUnits
  },
  {
    path: '/powerDistributionUnit/:id',
    name: 'PowerDistributionUnitDetail',
    component: PowerDistributionUnitDetail,
    props: true
  },
  {
    path: '/powerPanels',
    name: 'PowerPanels',
    component: PowerPanels
  },
  {
    path: '/powerPanel/:id',
    name: 'PowerPanelDetail',
    component: PowerPanelDetail,
    props: true
  },
  {
    path: '/powerOutlets',
    name: 'PowerOutlets',
    component: PowerOutlets
  },
  {
    path: '/powerOutlet/:id',
    name: 'PowerOutletDetail',
    component: PowerOutletDetail,
    props: true
  },
  {
    path: '/powerFeeds',
    name: 'PowerFeeds',
    component: PowerFeeds
  },
  {
    path: '/powerFeed/:id',
    name: 'PowerFeedDetail',
    component: PowerFeedDetail,
    props: true
  },

  // Core application routes
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn
  },
//  {
//    path: '/users',
//    name: 'Users',
//    component: Users,
//    meta: { requiresAuth: true }
//  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
];

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const auth = useAuth();
  
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'SignIn', query: { redirect: to.fullPath } });
  } else if (to.name === 'SignIn' && auth.isAuthenticated) {
    next({ name: 'Dashboard' });
  } else {
    next();
  }
});

export default router;
