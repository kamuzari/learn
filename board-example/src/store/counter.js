import {ref, computed} from 'vue'
import {defineStore} from 'pinia'

export const useCounterStore = defineStore('counter', () => {
    let id = 0
    const todos = ref([
        {id: id++, text: '할일1', isDone: false},
        {id: id++, text: '할일2', isDone: false}
    ])

    const add = (todoText) => {
        todos.value.push({
            id: id++,
            text: todoText,
            isDone: false
        })
    }

    const remove = (removal) => {
        const removalIndex = todos.value.findIndex((todo) => todo.id === removal);
        todos.value.splice(removalIndex, 1);
    }

    const update = (id) => {
        todos.value.map((todo) => {
            if (todo.id === id) {
                todo.isDone = !todo.isDone;
            }
        })
    }
    const countDone = computed(() => {
        return todos.value.filter(todo => todo.isDone).length;
    })

    return {todo: todos, add, remove, update, countDone};
},{persist:true})