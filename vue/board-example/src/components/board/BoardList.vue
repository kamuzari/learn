<template>
    <v-container class="text-center position-relative" v-if="!boards">
        <v-progress-circular :size="300" :width="30" color="blue" indeterminate></v-progress-circular>
    </v-container>

    <v-card v-if="boards">
        <v-data-iterator :items="boards" :items-per-page="18" :search="search">
            <template v-slot:header>
                <v-toolbar class="px-2">
                    <v-text-field v-model="search" density="comfortable" placeholder="Search"
                        prepend-inner-icon="mdi-magnify" style="max-width: 300px;" variant="solo" clearable
                        hide-details></v-text-field>
                </v-toolbar>
                <v-container>
                    <v-row align="center" justify="center">
                        <v-col cols="auto">
                            <v-btn size="x-large" density="compact" icon="mdi-plus" @click="moveWrite"> </v-btn>
                        </v-col>
                    </v-row>
                </v-container>
            </template>


            <template v-slot:default="{ items }">
                <v-container class="pa-2" fluid>
                    <v-row dense>
                        <v-col v-for="  item   in   items  " :key="item.raw.id" cols="auto" md="4">
                            <v-card class="pb-3" border flat>
                                <v-img :src="item.raw.url ? item.raw.url : defaultImage" max-height="200"></v-img>
                                <v-list-item :subtitle="item.raw.writer" class="mb-2">
                                    <template v-slot:title>
                                        <strong class="text-h6 mb-2">{{ item.raw.title }}</strong>
                                    </template>
                                </v-list-item>

                                <div class="d-flex justify-space-between px-4">
                                    <div class="d-flex align-center text-caption text-medium-emphasis me-1">
                                        <v-icon icon="mdi-clock" start></v-icon>

                                        <div class="d-inline-block text-truncate" style="max-width: 300px;">
                                            {{ item.raw.text }}
                                        </div>
                                    </div>

                                    <v-btn class="text-none" size="small" text="Read" border flat
                                        :to="{ name: 'detail', params: { boardId: item.raw.id } }"></v-btn>
                                </div>
                            </v-card>
                        </v-col>
                    </v-row>
                </v-container>
            </template>

            <template v-slot:footer="{ page, pageCount, prevPage, nextPage }">
                <div class="d-flex align-center justify-center pa-4">
                    <v-btn :disabled="page === 1" density="comfortable" icon="mdi-arrow-left" variant="tonal" rounded
                        @click="prevPage"></v-btn>

                    <div class="mx-2 text-caption">
                        Page {{ page }} of {{ pageCount }}
                    </div>

                    <v-btn :disabled="page >= pageCount" density="comfortable" icon="mdi-arrow-right" variant="tonal"
                        rounded @click="nextPage"></v-btn>
                </div>
            </template>
        </v-data-iterator>
    </v-card>
</template>
  
<script setup>
import defaultImage from '@/assets/ssafy_logo.png';
import { useStore } from '@/store/BoardStore.js';
import { ref, shallowRef } from 'vue';
import { useRouter } from 'vue-router';

const search = shallowRef('')
const boards = ref(null);
const router = useRouter();

setTimeout(() => {
    console.log('하는중!')
    boards.value = useStore().getAll()
}, 2000)

function moveWrite() {
    router.push({ name: 'new' });
}
</script>

<style lang="scss" scoped></style>