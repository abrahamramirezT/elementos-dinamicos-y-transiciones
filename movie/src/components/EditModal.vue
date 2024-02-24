<template>
  <div>
    <b-button
      style="width: 100%; padding: 8px"
      v-b-modal="`modalEdicion_${pelicula.id}`"
      @click="fillForm"
      >Editar</b-button
    >
    <b-modal
      :id="`modalEdicion_${pelicula.id}`"
      title="Editar Pelicula"
      hide-footer
    >
      <template #modal-header="{ close }">
        <h5>Agregar Pelicula</h5>
        <b-button size="sm" variant="outline-danger" @click="close()">
          X
        </b-button>
      </template>
      <form id="form" @submit.prevent="checkForm">
        <div v-if="errors.length">
          <b-alert show variant="danger">
            <b>Por favor, corrija el(los) siguiente(s) error(es):</b>
            <ul>
              <li v-for="error in errors" :key="error">{{ error }}</li>
            </ul>
          </b-alert>
        </div>
        <div class="fieldContainer">
          <div class="labelContainer">
            <label for="name">Nombre: </label>
          </div>
          <div class="inputContainer">
            <input id="name" v-model="name" type="text" name="name" />
          </div>
        </div>
        <div class="fieldContainer">
          <div class="labelContainer">
            <label for="description">Descripción: </label>
          </div>
          <div class="inputContainer">
            <input
              id="description"
              v-model="description"
              type="text"
              name="description"
            />
          </div>
        </div>

        <div class="fieldContainer">
          <div class="labelContainer">
            <label for="directorName">Nombre del director: </label>
          </div>
          <div class="inputContainer">
            <input
              id="directorName"
              v-model="directorName"
              type="text"
              name="directorName"
            />
          </div>
        </div>

        <div class="fieldContainer">
          <div class="labelContainer">
            <label for="publicationDate">Fecha de publicación: </label>
          </div>
          <div class="inputContainer">
            <input
              id="publicationDate"
              v-model="publicationDate"
              type="date"
              name="publicationDate"
            />
          </div>
        </div>

        <div class="fieldContainer">
          <div class="labelContainer">
            <label for="category">Categoría</label>
          </div>
          <div class="inputContainer">
            <select id="category" v-model="selected_category" name="category">
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

        <div class="buttonsContainer">
          <button type="submit" id="botonEnviar">Enviar</button>
          <button type="button" @click="closeModal" id="botonCancelar">
            Cancelar
          </button>
        </div>
      </form>
    </b-modal>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "EditModal",
  props: {
    pelicula: {
      type: Object,
      required: true,
    },
  },
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
    fillForm() {
      this.name = this.pelicula.name;
      this.description = this.pelicula.description;
      this.selected_category = this.pelicula.category.id;
      this.directorName = this.pelicula.directorName;
      this.publicationDate = this.pelicula.publicationDate;
    },
    checkForm() {
      if (
        this.name &&
        this.description &&
        this.selected_category &&
        this.directorName &&
        this.publicationDate
      ) {
        this.sendForm();
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
    sendForm() {
      axios
        .patch(`http://localhost:8080/api/movies/${this.pelicula.id}`, {
          name: this.name,
          description: this.description,
          directorName: this.directorName,
          publicationDate: this.publicationDate,
          category: {
            id: this.selected_category,
          },
        })
        .then((response) => {
          console.log(response);
          this.$emit("actualizacionExitosa");
          this.$swal({
            title: "Actualizada",
            text: "La película ha sido actualizada con éxito",
            icon: "success",
          });
          this.$root.$emit(
            "bv::hide::modal",
            `modalEdicion_${this.pelicula.id}`
          );
        })
        .catch((error) => {
          console.log(error);
        });
    },
    closeModal() {
      this.$root.$emit("bv::hide::modal", `modalEdicion_${this.pelicula.id}`);
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

<style scoped>
.buttonsContainer {
  width: 100%;
  display: flex;
  justify-content: space-evenly;
}

.buttonsContainer button {
  width: 30%;
  padding: 10px 20px;
  border-radius: 10px;
  border: none;
  margin-bottom: 10px;
  margin-top: 10px;
  transition: width 0.3s;
}

.buttonsContainer button:hover {
  width: 35%;
}

#botonEnviar {
  background-color: rgb(51, 139, 240);
  color: white;
}

#botonCancelar {
  background-color: rgb(240, 51, 51);
  color: white;
}

#form {
  width: 100%;
  padding: 10px;
}

.fieldContainer {
  width: 100%;
  margin-bottom: 20px;
}

.labelContainer {
  margin-bottom: 10px;
}

.inputContainer {
  width: 100%;
}

.inputContainer input {
  padding: 10px;
  width: 100%;
  border: 2px solid #ccc;
  border-radius: 10px;
  background-color: #f9f9f9;
  color: #333;
  outline: none;
}

.inputContainer input:focus {
  border-color: #2b2b2b;
}
.inputContainer textarea {
  padding: 10px;
  width: 100%;
  border: 2px solid #ccc;
  border-radius: 10px;
  background-color: #f9f9f9;
  color: #333;
  outline: none;
}

.inputContainer textarea:focus {
  border-color: #2b2b2b;
}

.inputContainer select {
  padding: 10px;
  width: 100%;
  border: 2px solid #ccc;
  border-radius: 10px;
  background-color: #f9f9f9;
  color: #333;
  outline: none;
}

.inputContainer select:focus {
  border-color: #2b2b2b;
}
</style>
