<form enctype="multipart/form-data">

    <h1>Work with Us</h1>

    <input type="text" id="nombre" name="nombre" placeholder="Name" required>
    <input type="email" id="email" name="email" placeholder="Email" required>
    <input type="tel" id="fphone" name="fphone" placeholder="Phone number"
        pattern="^(\+?\s?34\s?)?(\s*\d\s*){9}$" required>

    <label for="mensaje">Select your CV (PDF, JPG, JPEG, or PNG):</label>
    <input type="file" id="archivo" accept=".pdf, .jpg, .jpeg, .png" name="archivo" required>

    <input type="submit" class="botones" value="Submit Application">

</form>
<div class="img">
    <img src="src/trabajaconnostros.jpg" alt="Work with us">
</div>
