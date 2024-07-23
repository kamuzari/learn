<template>
  <div>
    <span @click="update(todo.id)" :class="{ 'is-done': todo.isDone }">
        {{ todo.text }}
      </span>
    <button @click="remove(todo.id)"> x</button>
  </div>
</template>

<script setup>
import {useCounterStore} from '../../store/BoardStore.js'

defineProps({todo: Object})
const store = useCounterStore();
const remove = (id) => {
  store.remove(id);
}

const update = (id) => {
  store.update(id);
}

</script>

<style scoped>
.is-done {
  text-decoration: line-through;
}

</style>