<template>
  <v-toolbar>
    <v-toolbar-title>
      <router-link :to="{ name: 'home' }"> 게시판 프로젝트</router-link>
    </v-toolbar-title>
    <v-spacer></v-spacer>
    <v-toolbar-items class="hidden-sm-and-down">

      <v-btn flat>
        <router-link :to="{ name: 'board', params: { category: 'notice' } }">공지사항</router-link>
      </v-btn>
      <v-btn flat>
        <router-link :to="{ name: 'board', params: { category: 'qna' } }">Q&A</router-link>
      </v-btn>
    </v-toolbar-items>
  </v-toolbar>
  <router-view></router-view>
</template>