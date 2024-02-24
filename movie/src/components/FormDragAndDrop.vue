<template>
  <div
    class="container drag-item"
    draggable="true"
    @dragstart="handleDragStart"
    @dragend="handleDragEnd"
    id="formularioDrag"
  >
    <form id="form">
      <div v-if="errors.length">
        <b-alert show variant="danger">
          <b>Por favor, corrija el(los) siguiente(s) error(es):</b>
          <ul>
            <li v-for="error in errors" :key="error">{{ error }}</li>
          </ul>
        </b-alert>
      </div>
      <div class="row">
        <div class="col">
          <div class="fieldContainer">
            <div class="labelContainer">
              <label for="name">Nombre: </label>
            </div>
            <div class="inputContainer">
              <input id="name" v-model="name" type="text" name="name" class="custom-input" />
            </div>
          </div>
        </div>
        <div class="col">
          <div class="fieldContainer">
            <div class="labelContainer">
              <label for="directorName">Nombre del Director: </label>
            </div>
            <div class="inputContainer">
              <input
                id="directorName"
                v-model="directorName"
                name="directorName"
                type="text"
                class="custom-input"
              />
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col">
          <div class="fieldContainer">
            <div class="labelContainer">
              <label for="publicationDate">Fecha de publicación: </label>
            </div>
            <div class="inputContainer">
              <input
                id="publicationDate"
                v-model="publicationDate"
                name="publicationDate"
                type="date"
                class="custom-input"
              />
            </div>
          </div>
        </div>
        <div class="col">
          <div class="fieldContainer">
            <div class="labelContainer">
              <label for="category">Categoría</label>
            </div>
            <div class="inputContainer">
              <select id="category" v-model="selected_category" name="category" class="custom-select">
                <option
                  v-for="category in categories"
                  :key="category.id"
                  :value="category.id"
                >
                  {{ category.name }}
                </option>
              </select>
            </div>
          </div>
        </div>
      </div>
      <div class="fieldContainer">
        <div class="labelContainer">
          <label for="description">Descripción: </label>
        </div>
        <div class="inputContainer">
          <textarea
            id="description"
            v-model="description"
            name="description"
            rows="3"
            class="custom-textarea"
          ></textarea>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      errors: [],
      name: null,
      description: null,
      selected_category: null,
      directorName: null,
      publicationDate: null,
      categories: [],
    };
  },
  methods: {
    checkForm() {
      if (
        this.name &&
        this.description &&
        this.selected_category &&
        this.directorName &&
        this.publicationDate
      ) {
        return true;
      }
      this.errors = [];
      if (!this.name) {
        this.errors.push("El nombre es obligatorio.");
      }
      if (!this.description) {
        this.errors.push("La descripcion es obligatoria.");
      }
      if (!this.selected_category) {
        this.errors.push("Debes seleccionar una categoria.");
      }
      if (!this.directorName) {
        this.errors.push("El nombre del director es obligatorio");
      }
      if (!this.publicationDate) {
        this.errors.push("La fecha de publicación es obligatoria");
      }
    },
    clearErrors() {
      this.errors = [];
    },
    handleDragStart(event) {
      event.dataTransfer.setData(
        "application/json",
        JSON.stringify({
          name: this.name,
          description: this.description,
          directorName: this.directorName,
          publicationDate: this.publicationDate,
          category: {
            id: this.selected_category,
          },
        })
      );
    },
    handleDragEnd(event) {
      if (this.checkForm()) {
        this.clearErrors();
        this.name = null;
        this.description = null;
        this.selected_category = null;
        this.directorName = null;
        this.publicationDate = null;
      } else {
        console.log("Aún faltan datos por establecer");
      }
    },
  },
  mounted() {
    axios
      .get("http://localhost:8080/api/categories")
      .then((response) => {
        this.categories = response.data;
      })
      .catch((e) => {
        console.error("Error en la peticion: ", e);
      });
  },
};
</script>

<style>
#formularioDrag {
  background-color: #3d7abb;
;
  border-radius: 10px;
  margin-top: 50px;
}

.custom-input,
.custom-textarea,
.custom-select {
  padding: 10px;
  width: 100%;
  border: 2px solid #ccc;
  border-radius: 10px;
  background-color: #f9f9f9;
  color: #333;
  outline: none;
}

.custom-input:focus,
.custom-textarea:focus,
.custom-select:focus {
  border-color: #2b2b2b;
}
</style>
