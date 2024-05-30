import { fakerKO as faker } from "@faker-js/faker";
import { defineStore } from "pinia";
const dataCount = 5;
export const useStore = defineStore(
  "board",
  () => {
    const boards = createFakeData();

    function getAll() {
      return boards;
    }

    function add(title, writer, text) {
      boards.push(new Board(faker.string.uuid(), title, writer, text));
    }

    function get(boardId) {
      return boards.filter((board) => board.id === boardId);
    }

    function update(boardId, title, writer, text) {
      boards.map((board) => {
        if (board.id == boardId) {
          board.update(title, writer, text);
        }
      });
    }

    function remove(boardId) {
      const removalIndex = boards.findIndex((board) => board.id === boardId);
      boards.splice(removalIndex, 1);
    }

    return { getAll, add, get, update, remove };
  },
  { persist: true }
);

const createFakeData = () => {
  return Array.from({ length: dataCount }).map(
    (v) =>
      new Board(
        faker.string.uuid(),
        faker.lorem.sentence(),
        faker.person.fullName(),
        faker.lorem.paragraph()
      )
  );
};

class Board {
  constructor(id, title, writer, text) {
    this.id = id;
    this.title = title;
    this.writer = writer;
    this.text = text;
  }

  update(title, writer, text) {
    this.title = title;
    this.writer = writer;
    this.text = text;
  }
}
