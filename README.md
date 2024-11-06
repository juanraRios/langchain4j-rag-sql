# langchain4j-rag-sql

Proyecto para ver la integración de Spring con una IA(open-ai) y usando una generación aumentada de recuperación (RAG) para bases de datos SQL.

Vamos a conectar nuestra base de datos de películas a una IA para facilitar que cualquiera pueda hacerle preguntas al asistente sin necesidad
de conocer SQL.

Esto repositorio pertenece a un tutorial de www.adictosaltrabajo.com , la mejor web sobre tutoriales de tecnología y programación en castellano :).

## how to

La aplicación puede ejecutarse desde terminal : ```mvn spring-boot:run```.

## FAQs

Puedes interactuar por consola y probar a lanzarle preguntas como las siguientes:



```

User: ¿cuántas películas tenemos registradas?
Assistant: Tenemos registradas un total de 6008 películas.

User: ¿cual es la puntuación media?
Assistant: La puntuación media es de aproximadamente 5.81.

User: ¿película española con mejor puntuación?
Assistant: La película española con mejor puntuación es "Contratiempo" con una calificación promedio de 8.1.

User: Dime la película de mayor duración.
Assistant: La película de mayor duración es "La flor" con una duración de 808 minutos.

User: La película mas antigua.
Assistant: La película más antigua es "Der müde Tod", que fue lanzada en el año 2017.

User: La película estrenada recientemente.
Assistant: La película estrenada más recientemente es "Gang of Roses", que fue publicada el 20 de septiembre de 2019.

User: Película de acción mejor calificada.
Assistant: La película de acción mejor calificada es "Vikram Vedha" con una calificación promedio de 8.7.

User: ¿que genero de películas es el mejor valorado?
Assistant: El género de películas mejor valorado es el "Drama" con una calificación promedio de 6.21.

```
