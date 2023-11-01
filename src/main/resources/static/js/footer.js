document.addEventListener("DOMContentLoaded", function () {
  // JavaScript para obtener la fecha y hora actual y mostrarla en el elemento "fecha-hora"
  function mostrarFechaYHora() {
    const fechaHoraElement = document.getElementById("fecha-hora");
    const fechaActual = new Date();
    const options = {
      year: "numeric",
      month: "long",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
      second: "2-digit",
    };
    const fechaHoraFormateada = fechaActual.toLocaleDateString(
      "es-ES",
      options
    );
    fechaHoraElement.textContent = fechaHoraFormateada;
  }

  // Llama a la función para mostrar la fecha y hora cuando se carga la página
  mostrarFechaYHora();
});
