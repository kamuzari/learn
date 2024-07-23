<script setup>
const emit = defineEmits(['response'])


const send= ()=>{
  emit('response', {
    code: 200,
    mssage: 'hello from child'
  })
}

const sendError = ()=>{
  emit('response',{
    code: 500,
    message: 'Internal server error'
  })
}

</script>

<template>
  <button @click="send">
    send[success]
  </button>

  <button @click="sendError">
    send[error]
  </button>
  <h2>Child component</h2>
</template>