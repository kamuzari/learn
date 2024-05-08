<script setup>
import {ref} from 'vue'
const fullNameClassBinding = ref('full-name-title');
const shortcutClassBinding = ref('short-cut-title');
</script>

<template>
  <h1 :class="shortcutClassBinding">이것이 short cut 속성 바인딩이다 :)</h1>
  <h1 v-bind:class="fullNameClassBinding">이것이 full name 속성 바인딩이다 :)</h1>
</template>

<style scoped>
.full-name-title{
  color: lightcoral;
}

.short-cut-title{
  color: deepskyblue;
}
</style>