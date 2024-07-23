<script setup>
import {onMounted, ref} from 'vue';
import {useCounterStore} from '../../store/BoardStore.js'
import TodoListItem from "@/views/todo/TodoListItem.vue";

// 컴포넌트 어디에서나 `store` 변수에 액세스 ✨
const store = useCounterStore();

</script>

<template>
  <h1> todo list</h1>
  <div>
    <todo-list-item v-for="todo in store.todo"
    :key="todo.id"
    :todo="todo"/>
  </div>
</template>

<style scoped>

</style>