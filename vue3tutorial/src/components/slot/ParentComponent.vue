<script>
import ChildComp from './ChildComp.vue'

export default {
  components: {
    ChildComp
  },
  data() {
    return {
      msg: 'Vue는 개발자에게 정말 유용하죠! 🎁'
    }
  }
}
</script>

<template>
  <ChildComp>부모 컨텐츠에서 자식에게 전달! ;)</ChildComp>
</template>