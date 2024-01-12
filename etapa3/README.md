# Proiect GlobalWaves  - Etapa 3

Tomita Ionut 325CD

Pentru rezolvarea etapei III am folosit ca schelet rezolvarea oficială a 
etapei II.


## Design Patterns:

- **Singleton** - pentru a crea o singură instanță a clasei 'LybraryInput' 
deoarece aceasta nu se modifica in timpul rulării programului.

- **Factory** - pentru a crea obiecte de tip UserAbstract in functie de tipul de
user pe care il primesc ca parametru din comanda de input.

- **Builder** - pentru a crea obiecte de tipul Playlist in functie de 
informatiile cunoscute

- **Command** - pentru a executa comenzile de navigare nextPage si previousPage


## Dificultati si flow:

- Una dintre metodele care mi-a dat cea mai mare bataie de cap a fost wrapped.
Atunci cand se dadea load unei melodii faceam update tuturor informatiilor
pentru User legate de melodie, dar si infomatiilor pentru artistul melodiei.
Daca se dadea load unui album sau podcast atunci salvam albumul sau podcastul
impreuna cu momentul de timp in care a inceput sa fie ascultat. Folosind 
aceste informatii puteam sa imi dau seama cate melodii din acel album/podcast
au fost ascultate si sa actualizez informatiile pentru User si Artist/Host
atunci cand se da comanda wrapped. De asemenea mai salvam si momentul de timp
in care se da wrapped atunci cand nu au cantat toate melodiile din album/podcast
pentru a putea calcula melodiile care au fost ascultate deja si a actualiza
corect informatiile


- Alta metoda putin mai dificila a fost cea de endProgram. De fiecare data cand
am interactionat cu un fisier, am actualizat Statisticile generale ale pro-
gramului salvand artistul fisierului. Pe durata prgramului informatiile 
artistilor au mai fost actualizate, de ex. cand se cumpara un merch al artistu-
lui. La final am sortat artitii dupa venitul total si i-am adaugat intr-o noua
statistica generala alaturi de informatiile personale.


- UpdateRecommendations - o metoda interesanta, dar la fel a fost destul de
dificil de implementat. Am incercat sa sparg problema in mai multe metode mai
mici pentru a o rezolva mai usor. Am utilizat stream-uri pentru a obtine unele
informatii cum ar fi top3Genres, top5fans, top5Songs


## Obiective:

Cu toate acestea inca nu am implementat logica din spate pentru cazurile de
monetization, astfel comanda endProgram nu este completa, deoarece artistii nu
isi primesc banii din Add-uri si nici atunci cand utilizatorul este premium si
asculta melodiile acestora, dar urmeaza sa o fac in viitorul apropiat.


### Feedback:

A fost un proiect destul de interesant care m-a ajutat sa invat foarte multe
legat de Java, Programare Orientata pe Obiecte, Design Patterns si nu numai.
Etapa I a fost cea mai dificila pentru mine, deoarece nu aveam idee cum sa
incep sau sa abordez problema. Etapa II a fost mai usoara, deoarece aveam 
scheletul de la etapa I si am putut sa il modific. Etapa III a fost ok.

Multumesc pentru aceasta tema!!!