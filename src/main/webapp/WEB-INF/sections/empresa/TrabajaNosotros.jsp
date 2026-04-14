 <form enctype="multipart/form-data">

            <h1>Trabaja con nosotros</h1>

            <input type="text" id="nombre" name="nombre" placeholder="Nombre" required>
            <input type="email" id="email" name="email" placeholder="Email" required>
            <input type="tel" id="fphone" name="fphone" placeholder="Número de teléfono"
                pattern="^(\+?\s?34\s?)?(\s*\d\s*){9}$" required>

            <label for="mensaje">Selecciona el CV (PDF, JPG, JPEG o PNG):</label>
            <input type="file" id="archivo" accept=".pdf, .jpg, .jpeg, .png" name="archivo" required>

            <input type="submit" class="botones" value="Enviar candidatura">

        </form>
        <div class="img">
            <img src="src/trabajaconnostros.jpg">
        </div>