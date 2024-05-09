<script setup>
const props = defineProps({
  msg: String,
  msg2: String,
  info: Object
})
</script>

<template>
  <h2>{{ msg || 'prop이 아직 전달되지 않았습니다!' }}</h2>
  <h2>{{ msg2 || 'prop이 아직 전달되지 않았습니다!' }}</h2>
  <h2>{{ info || 'info가 아직 전달되지 않았습니다!' }}</h2>

  <li v-for="information in info" :key="information.id">
    <span> {{ information.id }} </span>
    <span> {{ information.name }} </span>
    <span> {{ information.age }} </span>
  </li>
</template>