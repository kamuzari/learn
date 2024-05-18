<script setup>
import {ref} from 'vue';

let text = ref('');
</script>

<template>
<input v-model="text">
  <p>{{text}}</p>
</template>

<style scoped>

</style>