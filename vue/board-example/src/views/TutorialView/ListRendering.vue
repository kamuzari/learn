<script setup>
import {ref} from 'vue'

let id = 0;
const newTodo = ref('')
const todos = ref([
      {id: id++, text: 'vue tutorial 진행하기'},
      {id: id++, text: 'design pattern 학습하기'},
      {id: id++, text: '서비스 기업 최종합격하기'}
    ]
)

function add() {
  todos.value.push({id: id++, text: newTodo.value})
  newTodo.value='';
}

function removeTodo(removal) {
  todos.value = todos.value.filter(todo => todo!==removal);
}

</script>

<template>
  <form @submit.prevent="addTodo">
    <input v-model="newTodo">
    <button @click="add"> 할일 추가하기!</button>
  </form>
  <ul>
    <li v-for="todo in todos" :key="todo.id">
      {{ todo.text }}
      <button @click="removeTodo(todo)"> delete</button>
    </li>
  </ul>
</template>

<style scoped>

</style>