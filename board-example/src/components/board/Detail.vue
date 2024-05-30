<template>
    <v-skeleton-loader v-if="!board" class="mx-auto" elevation="12" max-width="400"
        type="table-heading, list-item-two-line, image, table-tfoot"></v-skeleton-loader>

    <v-container v-if="board">
        <v-row class="justify-center">
            <v-col cols="12" md="4">
                <v-card :subtitle="board.writer" :text="board.text" :title="board.title"></v-card>

                <div class="text-center text-caption">board</div>
            </v-col>

        </v-row>

        <v-row class="d-flex justify-center mb-6">
            <v-btn :to="{ name: 'board-list' }">
                목록으로 돌아가!
            </v-btn>

            <v-btn :to="{ name: 'edit', params: { editBoardId: boardId } }">
                수정하러 가기 !
            </v-btn>

            <v-btn @click="remove()">
                삭제 진행시켜 !
            </v-btn>

        </v-row>
    </v-container>
</template>

<script setup>
import { useStore } from '@/store/BoardStore';
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();
const boardStore = useStore();
const { boardId } = route.params;
const board = ref(null);
setTimeout(() => {
    board.value = boardStore.get(boardId).pop();

    if (!board) {
        router.push({ name: 'board-list' });
    }
}, 2000);

function remove() {
    boardStore.remove(boardId);
    router.push({ name: 'board-list' });
}

</script>

<style scoped></style>
