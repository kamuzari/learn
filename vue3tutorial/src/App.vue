<script setup>
import { ref } from "vue";

const container = ref('container')
</script>

<template>
  <div>
    <span>
      <RouterLink :to="{ name: 'home' }">홈으로 가기</RouterLink>
    </span>
    <span>
      <RouterLink :to="{ name: 'render-list' }">리스트 렌더링으로 가기</RouterLink>
    </span>
    <span>
      <RouterLink :to="{ name: 'form-binding' }">폼 바인딩으로 가기</RouterLink>
    </span>
    <span>
      <RouterLink :to="{ name: 'watchers' }">watchers로 가기</RouterLink>
    </span>
  </div>
  <router-view />
</template>

<style scoped>
div {
  justify-content: space-between;
  align-items: stretch;
}

span {
  flex: 20px;
  /* Optional: This makes the spans take equal space */
  text-align: center;
  /* Optional: Center the text inside the spans */
}
</style>