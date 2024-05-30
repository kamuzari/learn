<template>
    <form @submit.prevent="submit">
        <v-text-field v-model="title.value.value" :counter="50" :error-messages="title.errorMessage.value"
            label="Title"></v-text-field>

        <v-text-field v-model="writer.value.value" :counter="20" :error-messages="writer.errorMessage.value"
            label="writer"></v-text-field>

        <v-textarea v-model="text.value.value" :counter="1000" :error-messages="text.errorMessage.value" label="Text"
            clearable variant="outlined"></v-textarea>

        <v-checkbox v-model="checkbox.value.value" :error-messages="checkbox.errorMessage.value" label="Option"
            type="checkbox" value="1"></v-checkbox>

        <v-btn class="me-4" type="submit">
            submit
        </v-btn>

        <v-btn @click="handleReset">
            clear
        </v-btn>
    </form>
</template>

<script setup>
import router from '@/router';
import { useStore } from '@/store/BoardStore';
import { useField, useForm } from 'vee-validate';

const { handleSubmit, handleReset } = useForm({
    validationSchema: {
        title(value) {
            if (value?.length >= 2) return true

            return '2글자 이상의 제목을 작성해야해요'
        },
        writer(value) {
            if (value?.length >= 1) return true

            return '작성자는 반드시 입력하셔야 해요'
        },
        text(value) {
            if (value?.length >= 1) return true

            return '1글자 이상 작성해야 해요'
        },
        checkbox(value) {
            if (value === '1') return true

            return 'Must be checked.'
        },
    },
})

const boardStore = useStore();
const title = useField('title')
const writer = useField('writer')
const text = useField('text')
const checkbox = useField('checkbox')
const submit = handleSubmit(values => {
    boardStore.add(
        values.title,
        values.writer,
        values.text
    );

    router.push({ name: 'board-list' })
})
</script>