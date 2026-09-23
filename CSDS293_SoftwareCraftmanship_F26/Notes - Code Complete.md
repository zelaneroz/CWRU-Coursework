# CSDS 293: Software Craftsmanship
**Term**: Fall 2026
**Instructor**: Vincenzo Liberatore

# Ch 3. Measure Twice, Cut Once: Upstream Prerequisites

## 3.1 Importance of Prerequisites

* Software Construction accounts for 65% of total project cost of software development.
* Testing - if quality is an emphasis  at the end of a project; different from software quality assurance (testing is only a part of I).
* Construction Practices - Quality as emphasis at the middle of the project
* Planning, Requirements, & Design - Quality emphasized at the beginning of the project.
    * Poor requirements & project planning are the most common project risks in software development. It stems from: lack of expertise, some programmers can't resist coding immediately, and managers unsympathetic  to programmers spending time on construction prerequisites.

## 3.2 Determine the Kind of Software you're Working On
Different software projects require different balances between preparation and construction. Common project types include **business systems**, **mission-critical systems**, and **embedded life-critical systems**. 

### Different Systems, different approaches
**Business Systems**
* Examples: internet sites, intranet sites, inventory management, games, management information systems, and payroll systems.
* Usually favor **iterative approaches** such as Agile, Extreme Programming, Scrum, timebox development, and evolutionary prototyping.
    * Requirements can be relatively informal.
    * Planning, requirements, architecture, coding, testing, and QA can overlap. 
    * Devs tend to test their own code.
    * Deployment procedures may be informal.

**Mission-Critical Systems**
* Examples: embedded software, packaged software, software tools, web services, and some internet systems.
* Common **life-cycle models** include **staged delivery**, **evolutionary delivery**, and **spiral development**.
    * Typically involve basic up-front planning and formal change control.
    * Architectural design is usually done explicitly.
    * May use separate testing groups.
    * Deployment procedures tend to be formal.

**Embedded Life-Critical Systems**
* Examples: avionics software, medical devices, operating systems, and some packaged or embedded software.
* More **sequential** approaches are useful because requirements stability supports extremely high reliability. 
    * Use extensive up-front planning, testing planning, and QA planning.
    * Require rigorous change control. Requirements and designs are formally specified and inspected.
    * Code inspections and formal deployment procedures are common.
* The higher the cost of failure, the more formal the development process generally becomes. 

### Iterative vs. Sequential Development


| **Factor**                | **Sequential Approach**                            | **Iterative Approach**                             |
| ------------------------- | -------------------------------------------------- | -------------------------------------------------- |
| **Requirements**          | Fairly stable                                      | Unclear or likely to change                        |
| **Design**                | Straightforward and well understood                | Complex or challenging                             |
| **Team familiarity**      | Team knows the application domain                  | Team is unfamiliar with the application domain     |
| **Project risk**          | Relatively low                                     | High                                               |
| **Predictability**        | Long-term predictability is important              | Long-term predictability is less important         |
| **Cost of later changes** | Expensive                                          | Relatively inexpensive                             |
| **Best fit**              | Projects where stability and predictability matter | Projects where uncertainty and change are expected |




| **Approach**  | **What Happens** | **Development Cost** | **Rework Cost** | **Total Cost** |
| --------------|------------------| -------------------: | --------------: | -------------: |
| **Sequential without prerequisites** | Defects may remain undiscovered until late; rework is concentrated near the end.                                                                                                                       |         **$500,000** |    **$500,000** | **$1,000,000** |
| **Iterative without prerequisites**  | Defects are discovered closer to when they are introduced; rework is spread throughout development.                                                                                                                       |         **$500,000** |    **$375,000** |   **$875,000** |
| **Key lesson**                       | Iterative development reduces the impact of weak prerequisites, but does **not** eliminate the need for requirements, architecture, design, planning, and risk analysis. Late defects still require redesigning, recoding, and retesting. |                    — |               — |              — |


  **Why Iterative Rework Can Feel Cheaper**
* Costs are absorbed in small amounts throughout the project.
* Sequential projects may experience one large rework cost near the end.
* The total cost can still be substantial even if iterative rework feels less noticeable. 
* An iterative project with poor prerequisites can cost more than a sequential project with good prerequisites. 


**Real Projects Are Usually Hybrid**
* Most projects are neither completely sequential nor completely iterative. It is usually unrealistic to define **100% of requirements and design up front**.


**80% Requirements Approach**
* Specify about **80% of requirements up front**. Leave time for additional requirements to emerge later.
* Accept only the most valuable new requirements as the project progresses. 

**20% Requirements Approach**
* Specify only the most important **20% of requirements up front**. Develop the rest of the software incrementally.
* Define additional requirements and designs as development progresses. 


## 3.3 Problem-Definition Prerequisite
Product Vision/Vision Statement/Mission Statement/Product Definition: clear statement of what the problem is  without referring to possible solutions.
Problem definition comes first. Detailed requirements work comes afterward.

  **Good vs. Poor Problem Definition**
  | **Type** | **Example** | **Why** |
  |---|---|---|
  | **Good** | “We can't keep up with orders for the Gigatron.” | Clearly describes the problem*. |
  | **Poor** | “We need to optimize our automated data-entry system to keep up with orders for the Gigatron.” | Assumes a particular **solution** before fully understanding the problem. |

**Creating the Problem Definition**
* should normally be written in user language & describe the problem **from the user's perspective**.
* Do not assume every problem must be solved by writing software.
* Focus on the actual need, not on finding an excuse to build software.
* **Exception: Technical Problems**: Technical language is appropriate when the actual problem is with the computer or development environment. Examples include: Compile times are too slow, Programming tools are buggy.

## 3.5 Architecture Prerequisites
Software architecture/System Architecture/High-level Design/Top-level Design is the high-level structure of a software system.

  **Why Architecture Matters**
* **Conceptual integrity**: Good architecture helps maintain consistency from the highest-level design down to individual implementation details.
* It allows work to be divided so multiple developers or teams can work independently.
* **Good architecture makes construction easier; bad architecture can make construction nearly impossible.**
* Architectural mistakes are expensive to fix because they can affect large portions of the system. 
  **Typical Architectural Components**
* A good architecture should address the system's major structural and technical concerns.


### Architecture Components
**1. Major Classes**
* Responsibilities of each major class.
* Interactions between major classes.
* Class hierarchies.
* State transitions.
* Object persistence.
* Organization into subsystems when appropriate.
* Use the **80/20 rule**: specify roughly the 20% of classes responsible for 80% of system behavior. 

**2. Data Design**
* major files & table designs, alternatives considered, justify choices made.

**3. Business Rules**
* Identify business rules & impact these have on system's design.
* Ex. Business rule is that Customer info should be no more than 30s out of date. Thus it impacts the architecture's approach in keeping customer info up to date and synchronized.

**4. User Interface Design**
* Often specified at requirements. If not, specify in software architecture.
* Modularized so that a new user interface can be substituted  without affecting the business rules & output parts of the program.

**5. Resource Management**
Resources include memory, database connections, threads, and handles. Estimate the resources used for nominal and extreme cases. If an application is required to manage its own resources, this should also be described.

**6. Security**
* Build a threat mode
* Coding guidelines built with security implications  in mind (ex. Handling buffers, rules for handling untrusted data, encryption, protecting secret data in memory, etc).

**7. Performance**
If performance is a concern. Provide estimates and explain why you think these goals and estimates are achievable.

**8. Scalability**
Ability of a system to grow to meet future demands. Describe how the system will address growth in number of users, servers, network nodes, database records, size of database records, transaction volume, and so on.

**9. Interoperability**
If the system is expected to share data or resources with other software or hardware, the architecture should describe how that will be accomplished.

**10. Internationalization (I18n)/Localization**
* Preparing program to support multiple locales. Example: translating a program to support a specific local language

**11. I/O**
* specify a look-ahead, look-behind, or just-in-time reading scheme
* describe level at which I/O errors are detected: field, recorded, stream, or file level

**12. Error Pocesing**
* 90% of code is written for exception, error-processing cases; with so much cases dedicated to this, a strategy for handling them consistently should be spelled out in the architecture.

**13. Fault Tolerance**
* collection of techniques that increase a system's reliability  by detecting errors, recovering from them if possible, and containing their bad effects if not

**14. Architectural Feasibility**
* Any risk of infeasibility should be investigated through proof-of-concept prototypes, research, or other means.

**15. Overengineering**

**13. Fault Processing**




* Architecture should provide a clear **overview of the whole system**.
* Developers need to understand how individual classes and subsystems contribute to the overall program.
* The architecture should describe alternative organizations that were considered.
* It should explain **why the final organization was selected**.
* Design rationale is especially valuable during maintenance.
* The architecture should define the program's **major building blocks**.
* A building block may be a single class or an entire subsystem.
* Every feature in the requirements should be handled by at least one building block.
* Responsibilities should not conflict between building blocks. 
  **Single Responsibility and Information Hiding**
* Each building block should have a **well-defined responsibility**.
* A building block should know as little as possible about the internal responsibilities of other building blocks.
* Minimizing knowledge between components supports **information hiding**.
* Communication rules between building blocks should be explicitly defined.
* Architecture should specify which components may communicate directly, indirectly, or not at all. 
  

  **Security**
* Architecture should address both **design-level and code-level security**.
* A **threat model** should be created if one does not already exist.
* Security-related coding guidelines should address:

  * Buffer handling.
  * Untrusted input.
  * User data.
  * Cookies.
  * Configuration data.
  * External interfaces.
  * Encryption.
  * Error-message detail.
  * Protecting secrets stored in memory. 
    **Performance**
* Performance goals should be defined in the requirements when performance matters.
* Possible goals include:

  * Speed.
  * Memory usage.
  * Cost.
  * Resource consumption.
* Architecture should explain why performance goals are achievable.
* It should identify areas at risk of not meeting performance goals.
* It may specify particular algorithms or data structures needed for performance.
* Space and time budgets may be assigned to classes or objects. 
  **Scalability**
* **Scalability** is the ability of a system to grow to meet future demands.
* Architecture should address expected growth in:

  * Users.
  * Servers.
  * Network nodes.
  * Database records.
  * Record sizes.
  * Transaction volume.
* If scalability is not required, that assumption should be stated explicitly. 
  **Interoperability**
* If a system must share data or resources with other software or hardware, the architecture should explain how that interaction will occur. 
  **Internationalization and Localization**
* **Internationalization (I18n)** means designing software so it can support multiple locales.
* **Localization (L10n)** means adapting or translating software for a specific language or locale.
* Architecture should consider:

  * Character sets.
  * String formats.
  * Where strings are stored.
  * How strings can be changed without modifying code.
  * How translations affect the program and UI.
* Internationalization should be considered early for interactive systems. 
  **Input/Output**
* Architecture should define how input and output are handled.
* Possible reading approaches include:

  * **Look-ahead**
  * **Look-behind**
  * **Just-in-time**
* It should define where I/O errors are detected:

  * Field level.
  * Record level.
  * Stream level.
  * File level. 
    **Error Processing**
* Error handling should be designed at the **architectural level**, not left entirely to individual programmers.
* Error handling can make up a large portion of program code.
* A consistent systemwide error-handling strategy is therefore essential. 
  **Error-Handling Decisions**
* Decide whether error processing is **corrective or detective**.

  * **Corrective:** attempt to recover.
  * **Detective:** detect and report the problem.
* Decide whether error detection is **active or passive**.

  * **Active:** anticipate and check for errors.
  * **Passive:** react only when an error occurs.
* Decide how errors are propagated through the system.
* Establish consistent conventions for **error messages**.
* Define when exceptions are:

  * Thrown.
  * Caught.
  * Logged.
  * Documented.
* Decide where errors are handled:

  * At the point of detection.
  * In a dedicated error-handling class.
  * Higher in the call chain.
* Define which classes are responsible for validating input.
* Decide whether to use the programming environment's built-in exception mechanism or a custom approach. 
  **Fault Tolerance**
* **Fault tolerance** increases reliability by:

  * Detecting errors.
  * Recovering from errors when possible.
  * Limiting the effects of errors when recovery is impossible.
* Possible approaches include:

  * Retry after restoring a known-good state.
  * Use backup or alternative code.
  * Use multiple implementations and a voting algorithm.
  * Substitute a safe or harmless value.
  * Continue with reduced functionality.
  * Shut down.
  * Restart automatically. 
    **Architectural Feasibility**
* Architecture should demonstrate that the system is **technically feasible**.
* Risks may involve:

  * Performance targets.
  * Resource limits.
  * Implementation environments.
* Major uncertainties should be investigated before full construction.
* Techniques include:

  * Proof-of-concept prototypes.
  * Research.
  * Technical experiments. 
    **Overengineering**
* **Robustness** is the ability of a system to continue running after detecting an error.
* Architecture should define whether programmers should favor:

  * Extra robustness and defensive design.
  * The **simplest thing that works**.
* Expectations should be explicit so some components are not overengineered while others are barely adequate.
* Avoid adding complexity simply because it feels more professional or technically impressive. 
  **Buy vs. Build**
* Sometimes the best solution is **not to build software from scratch**.
* Existing commercial or open-source components may already solve the problem.
* Examples include:

  * GUI controls.
  * Database managers.
  * Image-processing tools.
  * Security and encryption components.
  * Spreadsheet tools.
  * Text-processing tools.
* If custom software is chosen instead, architecture should explain why it is better than existing alternatives. 
  **Reuse Decisions**
* Architecture should explain how existing software or materials will be reused.
* Reusable assets can include:

  * Existing code.
  * Test cases.
  * Data formats.
  * Libraries.
* Reused components should fit the system's other architectural goals. 
  **Change Strategy**
* Software is likely to change as programmers and users learn more during development.
* Changes may involve:

  * Data types.
  * File formats.
  * Functionality.
  * New features.
  * Future enhancements.
* Architecture should be flexible enough to accommodate **likely changes**.
* Ideally, a change should affect only a **small number of classes**. 
  **Designing for Change**
* Possible strategies include:

  * Version numbers in data files.
  * Reserved fields for future use.
  * Extensible file structures.
  * Tables instead of hard-coded conditional logic.
  * External configuration instead of values embedded in code.
* Architecture should use strategies that **delay commitment** when future requirements are uncertain. 
  **General Architectural Quality**
* Good architecture should explain:

  * Important classes.
  * Information hidden within each class.
  * Design alternatives considered.
  * Reasons alternatives were accepted or rejected.
* Architecture should feel like a **coherent conceptual whole** rather than a collection of unrelated additions.
* It should fit the problem naturally.
* Every major design decision should have a clear rationale.
* Avoid reasoning such as **“we've always done it that way.”** 
  **Machine and Language Independence**
* High-level architecture should be as **machine- and language-independent** as practical.
* Implementation details should not unnecessarily dictate system architecture.
* This reduces overdesign and keeps construction-level decisions at the appropriate level.
* Exception: this does not apply when the purpose of the software is specifically tied to a certain language or machine. 
  **Avoid Underarchitecture and Overarchitecture**
* Architecture must strike a balance between specifying too little and specifying too much.
* No area should receive more design attention than it deserves.
* All requirements should be addressed.
* Avoid **gold-plating**, which means adding features or architectural complexity that requirements do not call for. 
  **Risk Management**
* Architecture should explicitly identify risky areas.
* It should explain:

  * Why an area is risky.
  * How that risk has been investigated.
  * What is being done to reduce the risk.
* Major architectural risks should be addressed before construction progresses too far. 
  **Multiple Architectural Views**
* Architecture should provide **multiple views of the system**.
* Different views help reveal errors and inconsistencies.
* They also help programmers better understand how the system fits together.
* This is similar to a house having floor plans, elevations, electrical diagrams, and framing plans. 
  **Architecture Checklist**
  | **Area** | **Questions to Ask** |
  |---|---|
  | **Organization** | Is the overall structure clear and justified? |
  | **Building blocks** | Are responsibilities and interfaces well defined? |
  | **Requirements coverage** | Does every requirement map sensibly to system components? |
  | **Classes** | Are the critical classes described and justified? |
  | **Data** | Are data design and database organization explained? |
  | **Business rules** | Are important rules and their architectural effects identified? |
  | **UI** | Is the UI strategy defined and modularized? |
  | **I/O** | Is the input/output strategy defined? |
  | **Resources** | Are resource usage and management strategies defined? |
  | **Security** | Are security requirements addressed? |
  | **Performance** | Are speed and space goals defined? |
  | **Scalability** | Is future growth accounted for? |
  | **Interoperability** | Can the system interact with required external systems? |
  | **Internationalization** | Is there a strategy for localization and multiple locales? |
  | **Errors** | Is error handling consistent and coherent? |
  | **Fault tolerance** | Is the required fault-tolerance strategy defined? |
  | **Feasibility** | Has technical feasibility been established? |
  | **Overengineering** | Are expectations for robustness and complexity clear? |
  | **Buy vs. build** | Have existing solutions been evaluated? |
  | **Reuse** | Is reused software aligned with architectural goals? |
  | **Change** | Can the architecture accommodate likely future changes? | 
  **General Architecture Evaluation**
* The architecture should account for **all requirements**.
* It should be neither **overarchitected nor underarchitected**.
* The entire design should fit together conceptually.
* Top-level design should be largely independent of implementation language and machine.
* Major decisions should have clear motivations.
* Programmers who must implement the system should be **comfortable with and understand the architecture**. 
  **Main Takeaway**
* Architecture is the **high-level blueprint** that guides construction.
* Good architecture protects the system's conceptual integrity.
* Major decisions should be made deliberately and their reasoning documented.
* Architecture should address technical concerns that have **systemwide consequences**.
* It should be detailed enough to guide construction without unnecessarily restricting implementation.
* **Good architecture makes construction easier; bad architecture can make even the correct requirements difficult or impossible to implement successfully.**






# 4. Key Construction Design
## 4.3 
