<script setup>
import { ref } from "vue";

const container = ref('container')
</script>

<template>
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