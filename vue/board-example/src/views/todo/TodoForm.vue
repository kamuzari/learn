<script setup>
import { ref, watch } from 'vue'
import { useCounterStore } from '../../store/BoardStore.js'

const todoText = ref('asdas');
const store = useCounterStore();

const create = (text) => {
  store.add(text);
  todoText.value = '';
}

</script>

<template>
  <h1> todo form</h1>
  <div>
    <form @submit.prevent="create(todoText)">
      <input type="text" v-model="todoText">
      <input type="submit">
    </form>
  </div>
</template>

